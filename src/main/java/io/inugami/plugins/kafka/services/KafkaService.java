package io.inugami.plugins.kafka.services;

import io.inugami.api.ctx.BootstrapContext;
import io.inugami.api.ctx.DynamicEventProcessor;
import io.inugami.api.functionnals.ApplyIfNotNull;
import io.inugami.api.loggers.Loggers;
import io.inugami.api.models.data.basic.JsonObject;
import io.inugami.api.processors.ConfigHandler;
import io.inugami.api.spi.SpiLoader;
import io.inugami.commons.threads.MonitoredThreadFactory;
import io.inugami.plugins.kafka.commons.KafkaProducerHandler;
import io.inugami.plugins.kafka.provider.KafkaProviderHandler;
import io.inugami.plugins.kafka.provider.KafkaResultEvent;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaService implements BootstrapContext<Object>, ApplyIfNotNull {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final AtomicBoolean consume = new AtomicBoolean(true);

    private final AtomicBoolean produce = new AtomicBoolean(true);

    private final String providerName;

    private final Properties properties;

    private final KafkaConfig config;

    private final KafkaProviderHandler providerHandler;

    private final DynamicEventProcessor dynamicEventProcessor;

    private final String defaultChannel;

    private final Thread consumerThread;

    private final KafkaProducerHandler producerHandler;

    private final Thread producerThread;

    private final static ConcurrentLinkedQueue<JsonObject> producerQueue = new ConcurrentLinkedQueue<>();

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public KafkaService(final KafkaConfig config, final String providerName, final String defaultChannel,
                        final KafkaProviderHandler providerHandler, final boolean enableConsumer, final boolean enableProducer,
                        final KafkaProducerHandler producerHandler) {
        this.config           = config;
        this.providerName     = providerName;
        properties            = buildProperties(config);
        this.providerHandler  = providerHandler;
        this.defaultChannel   = defaultChannel;
        dynamicEventProcessor = new SpiLoader().loadSpiSingleService(DynamicEventProcessor.class);

        this.consumerThread = enableConsumer ? new MonitoredThreadFactory(KafkaService.class.getSimpleName(),
                                                                          true).newThread(new KafkaConsumerThread())
                                             : null;

        if (consumerThread != null) {
            consumerThread.start();
        }

        this.producerThread = enableConsumer ? new MonitoredThreadFactory(KafkaService.class.getSimpleName()
                                                                                  + "_producer",
                                                                          true).newThread(new KafkaProducerThread())
                                             : null;
        if (producerThread != null) {
            producerThread.start();
        }

        this.producerHandler = producerHandler;
    }

    // =========================================================================
    // INITIALIZE
    // =========================================================================
    public static <T> T resolveHandler(final ConfigHandler<String, String> config, final String propertyKey,
                                       final T defaultImplementation, final Class<? extends T> handlerClass) {
        T result = null;

        final String handlerName = config.optionnal().grab(propertyKey);

        if (handlerName != null) {
            result = new SpiLoader().loadSpiService(handlerName, handlerClass);
        }
        else {
            result = defaultImplementation;
        }
        return result;
    }

    private Properties buildProperties(final KafkaConfig config) {
        final Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getValueDeserializer());

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.getValueSerializer());

        /* OPTIONAL */
        //@formatter:off
        applyIfNotNull(config.getGroupInstanceId()                 , (value)->props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG,value));
        applyIfNotNull(config.getMaxPoolRecords()                  , (value)->props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,value));
        applyIfNotNull(config.getMaxPoolInterval()                 , (value)->props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,value));
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
    public void shutdown(final Object ctx) {
        consume.set(false);
        produce.set(false);
    }

    // =========================================================================
    // THREAD CONSUMER
    // =========================================================================
    private class KafkaConsumerThread implements Runnable {
        private Consumer<Long, String> consumer;

        Duration pollDuration;

        private void createConsumer() {
            if (consumer == null) {
                consumer = new KafkaConsumer<>(properties);
                consumer.subscribe(Collections.singletonList(config.getTopic()));

                final long pollDurationConfig = (long) (config.getMaxPoolInterval() * 0.8);
                pollDuration = Duration.ofMillis(pollDurationConfig);
            }

        }

        @Override
        public void run() {
            createConsumer();

            while (consume.get()) {
                final ConsumerRecords<Long, String> consumerRecords = consumer.poll(pollDuration);

                final Iterator<ConsumerRecord<Long, String>> records = consumerRecords.iterator();

                while (records.hasNext()) {
                    final ConsumerRecord<Long, String> record = records.next();
                    final List<KafkaResultEvent> providerResults = providerHandler.convertToEvents(providerName, record,
                                                                                                   defaultChannel);

                    for (final KafkaResultEvent resultSet : providerResults) {
                        dynamicEventProcessor.process(resultSet.getEvent(), resultSet.getProviderResult(),
                                                      resultSet.getChannel());
                    }
                }

                consumer.commitAsync();
            }

            consumer.close();
        }

    }

    // =========================================================================
    // THREAD PRODUCER
    // =========================================================================
    public synchronized void sendMessageToKafka(final List<? extends JsonObject> data) {
        if (data != null && !data.isEmpty()) {
            producerQueue.addAll(data);
        }
    }

    private class KafkaProducerThread implements Runnable {
        private final Producer<Long, String> producer = buildProducer();

        private Producer<Long, String> buildProducer() {
            return new KafkaProducer<>(properties);
        }

        @Override
        public void run() {
            final long time = System.currentTimeMillis();

            while (produce.get()) {
                while (!producerQueue.isEmpty()) {
                    final JsonObject data = producerQueue.poll();
                    sendToKafka(data);
                }
                producer.flush();
            }
            producer.close();
        }

        private void sendToKafka(final JsonObject data) {
            final JsonObject dataToSend = producerHandler == null ? data : producerHandler.convert(data);
            if (dataToSend != null) {
                final long timestamp = System.currentTimeMillis() * 1_000_000_000 + System.nanoTime();
                final ProducerRecord<Long, String> record = new ProducerRecord<>(config.getTopic(), timestamp,
                                                                                 dataToSend.convertToJson());
                try {
                    final RecordMetadata metadata = producer.send(record).get();
                }
                catch (final InterruptedException | ExecutionException e) {
                    Loggers.DEBUG.error(e.getMessage(), e);
                }
            }

        }

    }
}
