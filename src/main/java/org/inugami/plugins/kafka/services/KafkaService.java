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
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.inugami.api.ctx.BootstrapContext;

public class KafkaService implements Runnable, BootstrapContext<Object> {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    
    private boolean             consume           = true;
    
    private final Properties    properties;
    
    private final KafkaConfig   config;
    
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

        
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,config.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG,config.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,config.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,config.getValueDeserializer());
        
      

        /* OPTIONAL */
        config.getGroupInstanceId()
        config.getMaxPoolRecords()
        config.getMaxPoolInterval()
        config.getSessionTimeout()
        config.getHeartBeatMs()
        config.getEnableAutoCommit()
        config.getAutoComitIntervalMs()
        config.getPartitionAssignmentStrategy()
        config.getAutoOffsetRest()
        config.getFetchMinBytes()
        config.getFetchMaxBytes()
        config.getFetchMaxWaitMs()
        config.getMetadataMaxAge()
        config.getMaxPartitionFetchBytes()
        config.getSendBuffer()
        config.getReceiveBuffer()
        props.put(ProducerConfig.CLIENT_ID_CONFIG, config.getClientId());
        
        config.getClientRack()
        config.getReconnectBackoffMs()
        config.getRetryBackoff()
        config.getMetricSampleWindowMs()
        config.getMetricNumSamples()
        config.getMetricsRecordingLevel()
        config.getCheckCrcs()
        config.getConnectionsMaxIdleMs()
        config.getRequestTimeoutMs()
        config.getDefaultApiTimeoutMs()
        config.getExcludeInternalTopics()
        config.getDefaultExcludeInternalTopics()
        config.getLeaveGroupOnClose()
        config.getIsolationLevel()
        config.getAllowAutoCreateTopics()
        
        return props;
    }
    
    // =========================================================================
    // Runnable & BootstrapContext
    // =========================================================================
    @Override
    public void run() {
        consume();
        
    }
    
    @Override
    public void shutdown(final Object ctx) {
        consume = false;
    }
    
    // =========================================================================
    // CONSUMER
    // =========================================================================
    private void consume() {
        
        final Consumer<Long, String> consumer = createConsumer();
        
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
        consumer.close();
        System.out.println("DONE");
    }
    
    private Consumer<Long, String> createConsumer() {
        
        // Create the consumer using props.
        final Consumer<Long, String> consumer = new KafkaConsumer<>(properties);
        
        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList("test"));
        return consumer;
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
