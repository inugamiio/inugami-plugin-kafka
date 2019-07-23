package org.inugami.plugins.kafka.services;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.ByteBufferSerializer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.ExtendedSerializer;
import org.apache.kafka.common.serialization.FloatDeserializer;
import org.apache.kafka.common.serialization.FloatSerializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.ShortDeserializer;
import org.apache.kafka.common.serialization.ShortSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.inugami.api.exceptions.FatalException;
import org.inugami.api.processors.ConfigHandler;

import com.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;

public class KafkaConfigBuilder {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private String bootstrapServers;
    
    private String groupId;
    
    private String keyDeserializer;
    
    private String valueDeserializer;
    
    private String keySerializer;
    
    private String valueSerializer;
    
    private String topic;
    
    private long   timeout;
    
    /* OPTIONAL */
    
    private String  groupInstanceId;
    
    private Integer maxPoolRecords;
    
    private Integer maxPoolInterval;
    
    private Integer sessionTimeout;
    
    private Integer heartBeatMs;
    
    private Boolean enableAutoCommit;
    
    private Integer autoComitIntervalMs;
    
    private String  partitionAssignmentStrategy;
    
    private String  autoOffsetRest;
    
    private Integer fetchMinBytes;
    
    private Integer fetchMaxBytes;
    
    private Integer fetchMaxWaitMs;
    
    private Long    metadataMaxAge;
    
    private Integer maxPartitionFetchBytes;
    
    private Integer sendBuffer;
    
    private Integer receiveBuffer;
    
    private String  clientId;
    
    private String  clientRack;
    
    private Long    reconnectBackoffMs;
    
    private Long    retryBackoff;
    
    private Long    metricSampleWindowMs;
    
    private Integer metricNumSamples;
    
    private String  metricsRecordingLevel;
    
    private Boolean checkCrcs;
    
    private Long    connectionsMaxIdleMs;
    
    private Integer requestTimeoutMs;
    
    private Integer defaultApiTimeoutMs;
    
    private Boolean excludeInternalTopics;
    
    private Boolean leaveGroupOnClose;
    
    private String  isolationLevel;
    
    private Boolean allowAutoCreateTopics;
    
    /* PRODUCER */
    private Integer batchSize;
    
    private String  acks;
    
    private Long    lingerMs;
    
    private Integer deliveryTimeoutMs;
    
    private Integer maxRequestSize;
    
    private Long    maxBlockMs;
    
    private Long    bufferMemory;
    
    private String  compressionType;
    
    private Integer maxInFlightRequestsPerConnection;
    
    private Integer retries;
    
    private Boolean enableIdempotence;
    
    private Integer transactionTimeout;
    
    private String  transactionalId;
    
    //@formatter:off
    private final static Class<?>[] DESERIALIZERS = {
              ByteArrayDeserializer.class,
              ByteBufferDeserializer.class,
              BytesDeserializer.class,
              DoubleDeserializer.class,
              FloatDeserializer.class,
              IntegerDeserializer.class,
              LongDeserializer.class,
              ShortDeserializer.class,
              StringDeserializer.class,
              UUIDDeserializer.class
    };
    
    private final static Class<?>[] SERIALIZERS = {
            ByteArraySerializer.class,
            ByteBufferSerializer.class,
            BeanAsArraySerializer.class,
            DoubleSerializer.class,
            FloatSerializer.class,
            IntegerSerializer.class,
            LongSerializer.class,
            ShortSerializer.class,
            StringSerializer.class,
            UUIDSerializer.class
    };
    //@formatter:on
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public static KafkaConfig buildConfig(final String classBehaviorName, final ConfigHandler<String, String> config) {
        final KafkaConfigBuilder builder = new KafkaConfigBuilder();
        builder.setBootstrapServers(config.grabOrDefault("bootstrapServers", "localhost:9092"));
        builder.setTopic(config.grabOrDefault("topic", "test"));
        builder.setGroupId(config.grabOrDefault("groupId", classBehaviorName));
        builder.setTimeout(config.grabLong("timeout", 1000));
        
        builder.setKeyDeserializer(config.optionnal().grab("keyDeserializer"));
        builder.setKeySerializer(config.optionnal().grab("keySerializer"));
        
        builder.setValueDeserializer(config.optionnal().grab("valueDeserializer"));
        builder.setValueSerializer(config.optionnal().grab("valueSerializer"));
        
        /* OPTIONAL */
        builder.setGroupInstanceId(config.optionnal().grab("groupeInstanceId"));
        builder.setMaxPoolRecords(config.optionnal().grabInt("maxPoolRecords"));
        builder.setMaxPoolInterval(config.optionnal().grabInt("maxPoolInterval", 1000));
        builder.setSessionTimeout(config.optionnal().grabInt("sessionTimeout"));
        builder.setHeartBeatMs(config.optionnal().grabInt("heartBeatMs"));
        builder.setEnableAutoCommit(config.optionnal().grabBoolean("enableAutoComit", true));
        builder.setAutoComitIntervalMs(config.optionnal().grabInt("autoComitIntervalMs", 5000));
        builder.setPartitionAssignmentStrategy(config.optionnal().grab("partitionAssigmentStrategy"));
        builder.setAutoOffsetRest(config.optionnal().grab("autoOffsetRest"));
        builder.setFetchMinBytes(config.optionnal().grabInt("fetchMinBytes", 1));
        builder.setFetchMaxBytes(config.optionnal().grabInt("fetchMaxBytes", 5242880));
        builder.setFetchMaxWaitMs(config.optionnal().grabInt("fetchMaxWaitMs", 500));
        builder.setMetadataMaxAge(config.optionnal().grabLong("metadataMaxAge", 300000L));
        builder.setMaxPartitionFetchBytes(config.optionnal().grabInt("maxPartitionFetchBytes", 1048576));
        builder.setSendBuffer(config.optionnal().grabInt("sendBuffer", 131072));
        builder.setReceiveBuffer(config.optionnal().grabInt("receiveBuffer", 65536));
        builder.setClientId(config.optionnal().grabOrDefault("clientId", classBehaviorName));
        builder.setClientRack(config.optionnal().grab("clientRack"));
        builder.setReconnectBackoffMs(config.optionnal().grabLong("reconnectBackoffMs", 50L));
        builder.setRetryBackoff(config.optionnal().grabLong("retryBackoff", 100L));
        builder.setMetricSampleWindowMs(config.optionnal().grabLong("metricsSampleWindowMs", 30000L));
        builder.setMetricNumSamples(config.optionnal().grabInt("metricsNumSample", 30000));
        builder.setMetricsRecordingLevel(config.optionnal().grab("metricsRecordingLevel"));
        builder.setCheckCrcs(config.optionnal().grabBoolean("checkCrcs", true));
        builder.setConnectionsMaxIdleMs(config.optionnal().grabLong("connectionsMaxIdleMs", 60000L));
        builder.setRequestTimeoutMs(config.optionnal().grabInt("requestTimeout", 30000));
        builder.setDefaultApiTimeoutMs(config.optionnal().grabInt("defaultApiTimeoutMs", 60000));
        
        builder.setExcludeInternalTopics(config.optionnal().grabBoolean("excludeInternalTopics", true));
        builder.setLeaveGroupOnClose(config.optionnal().grabBoolean("leaveGroupOnClose", true));
        builder.setIsolationLevel(config.optionnal().grab("isolationLevel"));
        builder.setAllowAutoCreateTopics(config.optionnal().grabBoolean("allowAutoCreateTopics", true));
        
        /* PRODUCER */
        builder.setBatchSize(config.optionnal().grabInt("batchSize", 16384));
        builder.setAcks(config.optionnal().grab("acks", "1"));
        builder.setLingerMs(config.optionnal().grabLong("lingerMs", 0L));
        builder.setDeliveryTimeoutMs(config.optionnal().grabInt("deliveryTimeoutMs", 120000));
        builder.setMaxRequestSize(config.optionnal().grabInt("maxRequestSize", 1048576));
        builder.setMaxBlockMs(config.optionnal().grabLong("maxBlockMs", 60000));
        builder.setBufferMemory(config.optionnal().grabLong("bufferMemory", 33554432));
        builder.setCompressionType(config.optionnal().grab("compressionType", "none"));
        builder.setMaxInFlightRequestsPerConnection(config.optionnal().grabInt("maxInFlightRequestsPerConnection", 5));
        builder.setRetries(config.optionnal().grabInt("retries", 50));
        builder.setEnableIdempotence(config.optionnal().grabBoolean("enableIdempotence", false));
        builder.setTransactionTimeout(config.optionnal().grabInt("transactionTimeout", 60000));
        builder.setTransactionalId(config.optionnal().grab("transactionId"));
        
        return builder.build();
    }
    
    public KafkaConfig build() {
       //@formatter:off
        return new KafkaConfig(bootstrapServers,
                               groupId,
                               resolveDeserializer(keyDeserializer, LongDeserializer.class),
                               resolveDeserializer(keyDeserializer, StringDeserializer.class),
                               resolveSerializer(keySerializer, LongSerializer.class),
                               resolveSerializer(valueSerializer, StringSerializer.class),
                               topic,
                               timeout,
                               groupInstanceId,
                               maxPoolRecords,
                               maxPoolInterval,
                               sessionTimeout,
                               heartBeatMs,
                               enableAutoCommit,
                               autoComitIntervalMs,
                               partitionAssignmentStrategy,
                               autoOffsetRest,
                               fetchMinBytes,
                               fetchMaxBytes,
                               fetchMaxWaitMs,
                               metadataMaxAge,
                               maxPartitionFetchBytes,
                               sendBuffer,
                               receiveBuffer,
                               clientId,
                               clientRack,
                               reconnectBackoffMs,
                               retryBackoff,
                               metricSampleWindowMs,
                               metricNumSamples,
                               metricsRecordingLevel,
                               checkCrcs,
                               connectionsMaxIdleMs,
                               requestTimeoutMs,
                               defaultApiTimeoutMs,
                               excludeInternalTopics,
                               leaveGroupOnClose,
                               isolationLevel,
                               allowAutoCreateTopics,
                               batchSize,
                               acks,
                               lingerMs,
                               deliveryTimeoutMs,
                               maxRequestSize,
                               maxBlockMs,
                               bufferMemory,
                               compressionType,
                               maxInFlightRequestsPerConnection,
                               retries,
                               enableIdempotence,
                               transactionTimeout,
                               transactionalId);
       //@formatter:on
    }
    
    private Class<?> resolveDeserializer(final String value, final Class<?> defaultClass) {
        return resolverClass(value, defaultClass, DESERIALIZERS);
    }
    
    private Class<?> resolveSerializer(final String value, final Class<?> defaultClass) {
        return resolverClass(value, defaultClass, SERIALIZERS);
    }
    
    private Class<?> resolverClass(final String value, final Class<?> defaultClass, Class<?>[] classes) {
        Class<?> result = (value == null) || value.trim().isEmpty() ? defaultClass : null;
        
        if (result == null) {
            for (int i = classes.length - 1; i >= 0; i--) {
                if (classes[i].getName().equalsIgnoreCase(value)) {
                    result = classes[i];
                    break;
                }
            }
            
            if (result == null) {
                try {
                    result = this.getClass().forName(value);
                }
                catch (final ClassNotFoundException e) {
                    throw new FatalException(e.getMessage(), e);
                }
            }
            
        }
        
        return result;
    }
    
    // =========================================================================
    // OVERRIDES
    // =========================================================================
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("KafkaConfigBuilder [bootstrapServers=");
        builder.append(bootstrapServers);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", keyDeserializer=");
        builder.append(keyDeserializer);
        builder.append(", valueDeserializer=");
        builder.append(valueDeserializer);
        builder.append(", topic=");
        builder.append(topic);
        builder.append(", timeout=");
        builder.append(timeout);
        builder.append(", groupInstanceId=");
        builder.append(groupInstanceId);
        builder.append(", maxPoolRecords=");
        builder.append(maxPoolRecords);
        builder.append(", maxPoolInterval=");
        builder.append(maxPoolInterval);
        builder.append(", sessionTimeout=");
        builder.append(sessionTimeout);
        builder.append(", heartBeatMs=");
        builder.append(heartBeatMs);
        builder.append(", enableAutoCommit=");
        builder.append(enableAutoCommit);
        builder.append(", autoComitIntervalMs=");
        builder.append(autoComitIntervalMs);
        builder.append(", partitionAssignmentStrategy=");
        builder.append(partitionAssignmentStrategy);
        builder.append(", autoOffsetRest=");
        builder.append(autoOffsetRest);
        builder.append(", fetchMinBytes=");
        builder.append(fetchMinBytes);
        builder.append(", fetchMaxBytes=");
        builder.append(fetchMaxBytes);
        builder.append(", fetchMaxWaitMs=");
        builder.append(fetchMaxWaitMs);
        builder.append(", metadataMaxAge=");
        builder.append(metadataMaxAge);
        builder.append(", maxPartitionFetchBytes=");
        builder.append(maxPartitionFetchBytes);
        builder.append(", sendBuffer=");
        builder.append(sendBuffer);
        builder.append(", receiveBuffer=");
        builder.append(receiveBuffer);
        builder.append(", clientId=");
        builder.append(clientId);
        builder.append(", clientRack=");
        builder.append(clientRack);
        builder.append(", reconnectBackoffMs=");
        builder.append(reconnectBackoffMs);
        builder.append(", retryBackoff=");
        builder.append(retryBackoff);
        builder.append(", metricSampleWindowMs=");
        builder.append(metricSampleWindowMs);
        builder.append(", metricNumSamples=");
        builder.append(metricNumSamples);
        builder.append(", metricsRecordingLevel=");
        builder.append(metricsRecordingLevel);
        builder.append(", checkCrcs=");
        builder.append(checkCrcs);
        builder.append(", connectionsMaxIdleMs=");
        builder.append(connectionsMaxIdleMs);
        builder.append(", requestTimeoutMs=");
        builder.append(requestTimeoutMs);
        builder.append(", defaultApiTimeoutMs=");
        builder.append(defaultApiTimeoutMs);
        builder.append(", excludeInternalTopics=");
        builder.append(excludeInternalTopics);
        builder.append(", leaveGroupOnClose=");
        builder.append(leaveGroupOnClose);
        builder.append(", isolationLevel=");
        builder.append(isolationLevel);
        builder.append(", allowAutoCreateTopics=");
        builder.append(allowAutoCreateTopics);
        builder.append(", batchSize=");
        builder.append(batchSize);
        builder.append(", acks=");
        builder.append(acks);
        builder.append(", lingerMs=");
        builder.append(lingerMs);
        builder.append(", deliveryTimeoutMs=");
        builder.append(deliveryTimeoutMs);
        builder.append(", maxRequestSize=");
        builder.append(maxRequestSize);
        builder.append(", maxBlockMs=");
        builder.append(maxBlockMs);
        builder.append(", bufferMemory=");
        builder.append(bufferMemory);
        builder.append(", compressionType=");
        builder.append(compressionType);
        builder.append(", maxInFlightRequestsPerConnection=");
        builder.append(maxInFlightRequestsPerConnection);
        builder.append(", retries=");
        builder.append(retries);
        builder.append(", enableIdempotence=");
        builder.append(enableIdempotence);
        builder.append(", transactionTimeout=");
        builder.append(transactionTimeout);
        builder.append(", transactionalId=");
        builder.append(transactionalId);
        builder.append("]");
        return builder.toString();
    }
    
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
    public String getBootstrapServers() {
        return bootstrapServers;
    }
    
    public KafkaConfigBuilder setBootstrapServers(final String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
        return this;
    }
    
    public String getGroupId() {
        return groupId;
    }
    
    public KafkaConfigBuilder setGroupId(final String groupId) {
        this.groupId = groupId;
        return this;
    }
    
    public String getKeyDeserializer() {
        return keyDeserializer;
    }
    
    public KafkaConfigBuilder setKeyDeserializer(final String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
        return this;
    }
    
    public String getValueDeserializer() {
        return valueDeserializer;
    }
    
    public KafkaConfigBuilder setValueDeserializer(final String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
        return this;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public KafkaConfigBuilder setTopic(final String topic) {
        this.topic = topic;
        return this;
    }
    
    public long getTimeout() {
        return timeout;
    }
    
    public KafkaConfigBuilder setTimeout(final long timeout) {
        this.timeout = timeout;
        return this;
    }
    
    public String getKeySerializer() {
        return keySerializer;
    }
    
    public KafkaConfigBuilder setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
        return this;
    }
    
    public String getValueSerializer() {
        return valueSerializer;
    }
    
    public KafkaConfigBuilder setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
        return this;
    }
    
    // =========================================================================
    // OPTIONAL GETTERS & SETTERS
    // =========================================================================
    
    public String getGroupInstanceId() {
        return groupInstanceId;
    }
    
    public KafkaConfigBuilder setGroupInstanceId(final String groupInstanceId) {
        this.groupInstanceId = groupInstanceId;
        return this;
    }
    
    public Integer getMaxPoolRecords() {
        return maxPoolRecords;
    }
    
    public KafkaConfigBuilder setMaxPoolRecords(final Integer maxPoolRecords) {
        this.maxPoolRecords = maxPoolRecords;
        return this;
    }
    
    public Integer getMaxPoolInterval() {
        return maxPoolInterval;
    }
    
    public KafkaConfigBuilder setMaxPoolInterval(final Integer maxPoolInterval) {
        this.maxPoolInterval = maxPoolInterval;
        return this;
    }
    
    public Integer getSessionTimeout() {
        return sessionTimeout;
    }
    
    public KafkaConfigBuilder setSessionTimeout(final Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }
    
    public Integer getHeartBeatMs() {
        return heartBeatMs;
    }
    
    public KafkaConfigBuilder setHeartBeatMs(final Integer heartBeatMs) {
        this.heartBeatMs = heartBeatMs;
        return this;
    }
    
    public Boolean getEnableAutoCommit() {
        return enableAutoCommit;
    }
    
    public KafkaConfigBuilder setEnableAutoCommit(final Boolean enableAutoCommit) {
        this.enableAutoCommit = enableAutoCommit;
        return this;
    }
    
    public Integer getAutoComitIntervalMs() {
        return autoComitIntervalMs;
    }
    
    public KafkaConfigBuilder setAutoComitIntervalMs(final Integer autoComitIntervalMs) {
        this.autoComitIntervalMs = autoComitIntervalMs;
        return this;
    }
    
    public String getPartitionAssignmentStrategy() {
        return partitionAssignmentStrategy;
    }
    
    public KafkaConfigBuilder setPartitionAssignmentStrategy(final String partitionAssignmentStrategy) {
        this.partitionAssignmentStrategy = partitionAssignmentStrategy;
        return this;
    }
    
    public String getAutoOffsetRest() {
        return autoOffsetRest;
    }
    
    public KafkaConfigBuilder setAutoOffsetRest(final String autoOffsetRest) {
        this.autoOffsetRest = autoOffsetRest;
        return this;
    }
    
    public Integer getFetchMinBytes() {
        return fetchMinBytes;
    }
    
    public KafkaConfigBuilder setFetchMinBytes(final Integer fetchMinBytes) {
        this.fetchMinBytes = fetchMinBytes;
        return this;
    }
    
    public Integer getFetchMaxBytes() {
        return fetchMaxBytes;
    }
    
    public KafkaConfigBuilder setFetchMaxBytes(final Integer fetchMaxBytes) {
        this.fetchMaxBytes = fetchMaxBytes;
        return this;
    }
    
    public Integer getFetchMaxWaitMs() {
        return fetchMaxWaitMs;
    }
    
    public KafkaConfigBuilder setFetchMaxWaitMs(final Integer fetchMaxWaitMs) {
        this.fetchMaxWaitMs = fetchMaxWaitMs;
        return this;
    }
    
    public Long getMetadataMaxAge() {
        return metadataMaxAge;
    }
    
    public KafkaConfigBuilder setMetadataMaxAge(final Long metadataMaxAge) {
        this.metadataMaxAge = metadataMaxAge;
        return this;
    }
    
    public Integer getMaxPartitionFetchBytes() {
        return maxPartitionFetchBytes;
    }
    
    public KafkaConfigBuilder setMaxPartitionFetchBytes(final Integer maxPartitionFetchBytes) {
        this.maxPartitionFetchBytes = maxPartitionFetchBytes;
        return this;
    }
    
    public Integer getSendBuffer() {
        return sendBuffer;
    }
    
    public KafkaConfigBuilder setSendBuffer(final Integer sendBuffer) {
        this.sendBuffer = sendBuffer;
        return this;
    }
    
    public Integer getReceiveBuffer() {
        return receiveBuffer;
    }
    
    public KafkaConfigBuilder setReceiveBuffer(final Integer receiveBuffer) {
        this.receiveBuffer = receiveBuffer;
        return this;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public KafkaConfigBuilder setClientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }
    
    public String getClientRack() {
        return clientRack;
    }
    
    public KafkaConfigBuilder setClientRack(final String clientRack) {
        this.clientRack = clientRack;
        return this;
    }
    
    public Long getReconnectBackoffMs() {
        return reconnectBackoffMs;
    }
    
    public KafkaConfigBuilder setReconnectBackoffMs(final Long reconnectBackoffMs) {
        this.reconnectBackoffMs = reconnectBackoffMs;
        return this;
    }
    
    public Long getRetryBackoff() {
        return retryBackoff;
    }
    
    public KafkaConfigBuilder setRetryBackoff(final Long retryBackoff) {
        this.retryBackoff = retryBackoff;
        return this;
    }
    
    public Long getMetricSampleWindowMs() {
        return metricSampleWindowMs;
    }
    
    public KafkaConfigBuilder setMetricSampleWindowMs(final Long metricSampleWindowMs) {
        this.metricSampleWindowMs = metricSampleWindowMs;
        return this;
    }
    
    public Integer getMetricNumSamples() {
        return metricNumSamples;
    }
    
    public KafkaConfigBuilder setMetricNumSamples(final Integer metricNumSamples) {
        this.metricNumSamples = metricNumSamples;
        return this;
    }
    
    public String getMetricsRecordingLevel() {
        return metricsRecordingLevel;
    }
    
    public KafkaConfigBuilder setMetricsRecordingLevel(final String metricsRecordingLevel) {
        this.metricsRecordingLevel = metricsRecordingLevel;
        return this;
    }
    
    public Boolean getCheckCrcs() {
        return checkCrcs;
    }
    
    public KafkaConfigBuilder setCheckCrcs(final Boolean checkCrcs) {
        this.checkCrcs = checkCrcs;
        return this;
    }
    
    public Long getConnectionsMaxIdleMs() {
        return connectionsMaxIdleMs;
    }
    
    public KafkaConfigBuilder setConnectionsMaxIdleMs(final Long connectionsMaxIdleMs) {
        this.connectionsMaxIdleMs = connectionsMaxIdleMs;
        return this;
    }
    
    public Integer getRequestTimeoutMs() {
        return requestTimeoutMs;
    }
    
    public KafkaConfigBuilder setRequestTimeoutMs(final Integer requestTimeoutMs) {
        this.requestTimeoutMs = requestTimeoutMs;
        return this;
    }
    
    public Integer getDefaultApiTimeoutMs() {
        return defaultApiTimeoutMs;
    }
    
    public KafkaConfigBuilder setDefaultApiTimeoutMs(final Integer defaultApiTimeoutMs) {
        this.defaultApiTimeoutMs = defaultApiTimeoutMs;
        return this;
    }
    
    public Boolean getExcludeInternalTopics() {
        return excludeInternalTopics;
    }
    
    public KafkaConfigBuilder setExcludeInternalTopics(final Boolean excludeInternalTopics) {
        this.excludeInternalTopics = excludeInternalTopics;
        return this;
    }
    
    public Boolean getLeaveGroupOnClose() {
        return leaveGroupOnClose;
    }
    
    public KafkaConfigBuilder setLeaveGroupOnClose(final Boolean leaveGroupOnClose) {
        this.leaveGroupOnClose = leaveGroupOnClose;
        return this;
    }
    
    public String getIsolationLevel() {
        return isolationLevel;
    }
    
    public KafkaConfigBuilder setIsolationLevel(final String isolationLevel) {
        this.isolationLevel = isolationLevel;
        return this;
    }
    
    public Boolean getAllowAutoCreateTopics() {
        return allowAutoCreateTopics;
    }
    
    public KafkaConfigBuilder setAllowAutoCreateTopics(final Boolean allowAutoCreateTopics) {
        this.allowAutoCreateTopics = allowAutoCreateTopics;
        return this;
    }
    
    public Integer getBatchSize() {
        return batchSize;
    }
    
    public KafkaConfigBuilder setBatchSize(final Integer batchSize) {
        this.batchSize = batchSize;
        return this;
    }
    
    public String getAcks() {
        return acks;
    }
    
    public KafkaConfigBuilder setAcks(final String acks) {
        this.acks = acks;
        return this;
    }
    
    public Long getLingerMs() {
        return lingerMs;
    }
    
    public KafkaConfigBuilder setLingerMs(final Long lingerMs) {
        this.lingerMs = lingerMs;
        return this;
    }
    
    public Integer getDeliveryTimeoutMs() {
        return deliveryTimeoutMs;
    }
    
    public KafkaConfigBuilder setDeliveryTimeoutMs(final Integer deliveryTimeoutMs) {
        this.deliveryTimeoutMs = deliveryTimeoutMs;
        return this;
    }
    
    public Integer getMaxRequestSize() {
        return maxRequestSize;
    }
    
    public KafkaConfigBuilder setMaxRequestSize(final Integer maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
        return this;
    }
    
    public Long getMaxBlockMs() {
        return maxBlockMs;
    }
    
    public KafkaConfigBuilder setMaxBlockMs(final Long maxBlockMs) {
        this.maxBlockMs = maxBlockMs;
        return this;
    }
    
    public Long getBufferMemory() {
        return bufferMemory;
    }
    
    public KafkaConfigBuilder setBufferMemory(final Long bufferMemory) {
        this.bufferMemory = bufferMemory;
        return this;
    }
    
    public String getCompressionType() {
        return compressionType;
    }
    
    public KafkaConfigBuilder setCompressionType(final String compressionType) {
        this.compressionType = compressionType;
        return this;
    }
    
    public Integer getMaxInFlightRequestsPerConnection() {
        return maxInFlightRequestsPerConnection;
    }
    
    public KafkaConfigBuilder setMaxInFlightRequestsPerConnection(final Integer maxInFlightRequestsPerConnection) {
        this.maxInFlightRequestsPerConnection = maxInFlightRequestsPerConnection;
        return this;
    }
    
    public Integer getRetries() {
        return retries;
    }
    
    public KafkaConfigBuilder setRetries(final Integer retries) {
        this.retries = retries;
        return this;
    }
    
    public Boolean getEnableIdempotence() {
        return enableIdempotence;
    }
    
    public KafkaConfigBuilder setEnableIdempotence(final Boolean enableIdempotence) {
        this.enableIdempotence = enableIdempotence;
        return this;
    }
    
    public Integer getTransactionTimeout() {
        return transactionTimeout;
    }
    
    public KafkaConfigBuilder setTransactionTimeout(final Integer transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
        return this;
    }
    
    public String getTransactionalId() {
        return transactionalId;
    }
    
    public KafkaConfigBuilder setTransactionalId(final String transactionalId) {
        this.transactionalId = transactionalId;
        return this;
    }
    
}
