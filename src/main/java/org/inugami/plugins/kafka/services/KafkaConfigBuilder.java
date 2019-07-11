package org.inugami.plugins.kafka.services;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.FloatDeserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.ShortDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.inugami.api.exceptions.FatalException;

public class KafkaConfigBuilder {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private String bootstrapServers;
    
    private String groupId;
    
    private String keyDeserializer;
    
    private String valueDeserializer;
    
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
    
    private Long    metricNumSamples;
    
    private String  metricsRecordingLevel;
    
    private Boolean checkCrcs;
    
    private Long    connectionsMaxIdleMs;
    
    private Integer requestTimeoutMs;
    
    private Integer defaultApiTimeoutMs;
    
    private Boolean excludeInternalTopics;
    
    private Boolean defaultExcludeInternalTopics;
    
    private Boolean leaveGroupOnClose;
    
    private String  isolationLevel;
    
    private Boolean allowAutoCreateTopics;
    
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
    //@formatter:on
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public KafkaConfig build() {
       //@formatter:off
        return new KafkaConfig(bootstrapServers,
                               groupId,
                               resolveDeserializer(keyDeserializer, LongDeserializer.class),
                               resolveDeserializer(valueDeserializer, StringDeserializer.class),
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
                               defaultExcludeInternalTopics,
                               leaveGroupOnClose,
                               isolationLevel,
                               allowAutoCreateTopics);
       //@formatter:on
    }
    
    private Class<?> resolveDeserializer(final String value, final Class<?> defaultClass) {
        Class<?> result = (value == null) || value.trim().isEmpty() ? defaultClass : null;
        
        if (result == null) {
            for (int i = DESERIALIZERS.length - 1; i >= 0; i--) {
                if (DESERIALIZERS[i].getName().equalsIgnoreCase(value)) {
                    result = DESERIALIZERS[i];
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
        builder.append(", defaultExcludeInternalTopics=");
        builder.append(defaultExcludeInternalTopics);
        builder.append(", leaveGroupOnClose=");
        builder.append(leaveGroupOnClose);
        builder.append(", isolationLevel=");
        builder.append(isolationLevel);
        builder.append(", allowAutoCreateTopics=");
        builder.append(allowAutoCreateTopics);
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
    
    public Long getMetricNumSamples() {
        return metricNumSamples;
    }
    
    public KafkaConfigBuilder setMetricNumSamples(final Long metricNumSamples) {
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
    
    public Boolean getDefaultExcludeInternalTopics() {
        return defaultExcludeInternalTopics;
    }
    
    public KafkaConfigBuilder setDefaultExcludeInternalTopics(final Boolean defaultExcludeInternalTopics) {
        this.defaultExcludeInternalTopics = defaultExcludeInternalTopics;
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
}
