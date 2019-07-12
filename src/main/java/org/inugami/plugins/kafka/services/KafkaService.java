package org.inugami.plugins.kafka.services;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.inugami.api.ctx.BootstrapContext;
import org.inugami.api.functionnals.ApplyIfNotNull;

public class KafkaService implements Runnable, BootstrapContext<Object>, ApplyIfNotNull {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final static String    BOOTSTRAP_SERVERS = "localhost:9092";
    
    private boolean                consume           = true;
    
    private final Properties       properties;
    
    private final KafkaConfig      config;
    
    private Consumer<Long, String> consumer;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public KafkaService(final KafkaConfig config) {
        this.config = config;
        properties = buildProperties(config);
    }
    
    // =========================================================================
    // INITIALIZE
    // =========================================================================
    private Properties buildProperties(final KafkaConfig config) {
        final Properties props = new Properties();
        
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getValueDeserializer());
        
        /* OPTIONAL */
        //@formatter:off
        applyIfNotNull(config.getGroupInstanceId()                 , (value)->props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG,value));
        applyIfNotNull(config.getMaxPoolRecords()                  , (value)->props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,value));
        applyIfNotNull(config.getMaxPoolInterval()                 , (value)->props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,value));
        applyIfNotNull(config.getSessionTimeout()                  , (value)->props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,value));
        applyIfNotNull(config.getHeartBeatMs()                     , (value)->props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,value));
        applyIfNotNull(config.getEnableAutoCommit()                , (value)->props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,value));
        applyIfNotNull(config.getAutoComitIntervalMs()             , (value)->props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,value));
        applyIfNotNull(config.getPartitionAssignmentStrategy()     , (value)->props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,value));
        applyIfNotNull(config.getAutoOffsetRest()                  , (value)->props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,value));
        applyIfNotNull(config.getFetchMinBytes()                   , (value)->props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG,value));
        applyIfNotNull(config.getFetchMaxBytes()                   , (value)->props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG,value));
        applyIfNotNull(config.getFetchMaxWaitMs()                  , (value)->props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG,value));
        applyIfNotNull(config.getMetadataMaxAge()                  , (value)->props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG,value));
        applyIfNotNull(config.getMaxPartitionFetchBytes()          , (value)->props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,value));
        applyIfNotNull(config.getSendBuffer()                      , (value)->props.put(ConsumerConfig.SEND_BUFFER_CONFIG,value));
        applyIfNotNull(config.getReceiveBuffer()                   , (value)->props.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG,value));
        applyIfNotNull(config.getClientId()                        , (value)->props.put(ConsumerConfig.CLIENT_ID_CONFIG,value));

        applyIfNotNull(config.getClientRack()                      , (value)->props.put(ConsumerConfig.CLIENT_RACK_CONFIG,value));
        applyIfNotNull(config.getReconnectBackoffMs()              , (value)->props.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG,value));
        applyIfNotNull(config.getRetryBackoff()                    , (value)->props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG,value));
        applyIfNotNull(config.getMetricSampleWindowMs()            , (value)->props.put(ConsumerConfig.METRICS_SAMPLE_WINDOW_MS_CONFIG,value));
        applyIfNotNull(config.getMetricNumSamples()                , (value)->props.put(ConsumerConfig.METRICS_NUM_SAMPLES_CONFIG,value));
        applyIfNotNull(config.getMetricsRecordingLevel()           , (value)->props.put(ConsumerConfig.METRICS_RECORDING_LEVEL_CONFIG,value));
        applyIfNotNull(config.getCheckCrcs()                       , (value)->props.put(ConsumerConfig.CHECK_CRCS_CONFIG,value));
        applyIfNotNull(config.getConnectionsMaxIdleMs()            , (value)->props.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG,value));
        applyIfNotNull(config.getRequestTimeoutMs()                , (value)->props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG,value));
        applyIfNotNull(config.getDefaultApiTimeoutMs()             , (value)->props.put(ConsumerConfig.DEFAULT_API_TIMEOUT_MS_CONFIG,value));
        applyIfNotNull(config.getExcludeInternalTopics()           , (value)->props.put(ConsumerConfig.EXCLUDE_INTERNAL_TOPICS_CONFIG,value));
        applyIfNotNull(config.getDefaultExcludeInternalTopics()    , (value)->props.put(ConsumerConfig.DEFAULT_EXCLUDE_INTERNAL_TOPICS,value));
        applyIfNotNull(config.getLeaveGroupOnClose()               , (value)->props.put("internal.leave.group.on.close",value));
        applyIfNotNull(config.getIsolationLevel()                  , (value)->props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,value));
        applyIfNotNull(config.getAllowAutoCreateTopics()           , (value)->props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG,value));
       
        
        /* OPTIONAL PRODUCER */
        applyIfNotNull(config.getBatchSize()                         , (value)->props.put(ProducerConfig.BATCH_SIZE_CONFIG, value));
        applyIfNotNull(config.getAcks()                              , (value)->props.put(ProducerConfig.ACKS_CONFIG, value));
        applyIfNotNull(config.getLingerMs()                          , (value)->props.put(ProducerConfig.LINGER_MS_CONFIG, value));
        applyIfNotNull(config.getDeliveryTimeoutMs()                 , (value)->props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, value));
        applyIfNotNull(config.getMaxRequestSize()                    , (value)->props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, value));
        applyIfNotNull(config.getMaxBlockMs()                        , (value)->props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, value));
        applyIfNotNull(config.getBufferMemory()                      , (value)->props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, value));
        applyIfNotNull(config.getCompressionType()                   , (value)->props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, value));
        applyIfNotNull(config.getMaxInFlightRequestsPerConnection()  , (value)->props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, value));
        applyIfNotNull(config.getRetries()                           , (value)->props.put(ProducerConfig.RETRIES_CONFIG, value));
        applyIfNotNull(config.getEnableIdempotence()                 , (value)->props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, value));
        applyIfNotNull(config.getTransactionTimeout()                , (value)->props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, value));
        applyIfNotNull(config.getTransactionalId()                   , (value)->props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, value));
        
        //@formatter:on
        
        return props;
    }
    
    // =========================================================================
    // Runnable & BootstrapContext
    // =========================================================================
    @Override
    public void run() {
        
    }
    
    @Override
    public void shutdown(final Object ctx) {
        consume = false;
        if (consumer != null) {
            consumer.close();
        }
    }
    
    // =========================================================================
    // CONSUMER
    // =========================================================================
    private void consume() {
        
        final int giveUp = 100;
        int noRecordsCount = 0;
        
        while (consume) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
            
            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp)
                    break;
                else
                    continue;
            }
            
            consumerRecords.forEach(record -> {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n", record.key(), record.value(),
                                  record.partition(), record.offset());
            });
            
            consumer.commitAsync();
        }
        
    }
    
    public void createConsumer() {
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(config.getTopic()));
    }
    
    // =========================================================================
    // PRODUCER
    // =========================================================================
    public void runProducer(final int sendMessageCount) throws Exception {
        final Producer<Long, String> producer = createProducer();
        final long time = System.currentTimeMillis();
        
        try {
            for (long index = time; index < (time + sendMessageCount); index++) {
                final ProducerRecord<Long, String> record = new ProducerRecord<>("test", index, "Hello Mom " + index);
                
                final RecordMetadata metadata = producer.send(record).get();
                
                final long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
                                  record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
                producer.flush();
                
                Thread.currentThread().sleep(1000);
            }
        }
        finally {
            producer.flush();
            producer.close();
        }
    }
    
    private Producer<Long, String> createProducer() {
        return new KafkaProducer<>(properties);
    }
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
    
}
