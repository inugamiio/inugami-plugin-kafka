package org.inugami.plugins.kafka.services;

public class KafkaConfig {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final String   bootstrapServers;
    
    private final String   groupId;
    
    private final Class<?> keyDeserializer;
    
    private final Class<?> valueDeserializer;
    
    private final String   topic;
    
    private final long     timeout;
    
    /* OPTIONAL */
    private final String  groupInstanceId;
    
    private final Integer maxPoolRecords;
    
    private final Integer maxPoolInterval;
    
    private final Integer sessionTimeout;
    
    private final Integer heartBeatMs;
    
    private final Boolean enableAutoCommit;
    
    private final Integer autoComitIntervalMs;
    
    private final String  partitionAssignmentStrategy;
    
    private final String  autoOffsetRest;
    
    private final Integer fetchMinBytes;
    
    private final Integer fetchMaxBytes;
    
    private final Integer fetchMaxWaitMs;
    
    private final Long    metadataMaxAge;
    
    private final Integer maxPartitionFetchBytes;
    
    private final Integer sendBuffer;
    
    private final Integer receiveBuffer;
    
    private final String  clientId;
    
    private final String  clientRack;
    
    private final Long    reconnectBackoffMs;
    
    private final Long    retryBackoff;
    
    private final Long    metricSampleWindowMs;
    
    private final Long    metricNumSamples;
    
    private final String  metricsRecordingLevel;
    
    private final Boolean checkCrcs;
    
    private final Long    connectionsMaxIdleMs;
    
    private final Integer requestTimeoutMs;
    
    private final Integer defaultApiTimeoutMs;
    
    private final Boolean excludeInternalTopics;
    
    private final Boolean defaultExcludeInternalTopics;
    
    private final Boolean leaveGroupOnClose;
    
    private final String  isolationLevel;
    
    private final Boolean allowAutoCreateTopics;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    protected KafkaConfig(final String bootstrapServers, final String groupId, final Class<?> keyDeserializer,
                          final Class<?> valueDeserializer, final String topic, final long timeout,
                          final String groupInstanceId, final Integer maxPoolRecords, final Integer maxPoolInterval,
                          final Integer sessionTimeout, final Integer heartBeatMs, final Boolean enableAutoCommit,
                          final Integer autoComitIntervalMs, final String partitionAssignmentStrategy,
                          final String autoOffsetRest, final Integer fetchMinBytes, final Integer fetchMaxBytes,
                          final Integer fetchMaxWaitMs, final Long metadataMaxAge, final Integer maxPartitionFetchBytes,
                          final Integer sendBuffer, final Integer receiveBuffer, final String clientId,
                          final String clientRack, final Long reconnectBackoffMs, final Long retryBackoff,
                          final Long metricSampleWindowMs, final Long metricNumSamples,
                          final String metricsRecordingLevel, final Boolean checkCrcs, final Long connectionsMaxIdleMs,
                          final Integer requestTimeoutMs, final Integer defaultApiTimeoutMs,
                          final Boolean excludeInternalTopics, final Boolean defaultExcludeInternalTopics,
                          final Boolean leaveGroupOnClose, final String isolationLevel,
                          final Boolean allowAutoCreateTopics) {
        super();
        this.bootstrapServers = bootstrapServers;
        this.groupId = groupId;
        this.keyDeserializer = keyDeserializer;
        this.valueDeserializer = valueDeserializer;
        this.topic = topic;
        this.timeout = timeout;
        this.groupInstanceId = groupInstanceId;
        this.maxPoolRecords = maxPoolRecords;
        this.maxPoolInterval = maxPoolInterval;
        this.sessionTimeout = sessionTimeout;
        this.heartBeatMs = heartBeatMs;
        this.enableAutoCommit = enableAutoCommit;
        this.autoComitIntervalMs = autoComitIntervalMs;
        this.partitionAssignmentStrategy = partitionAssignmentStrategy;
        this.autoOffsetRest = autoOffsetRest;
        this.fetchMinBytes = fetchMinBytes;
        this.fetchMaxBytes = fetchMaxBytes;
        this.fetchMaxWaitMs = fetchMaxWaitMs;
        this.metadataMaxAge = metadataMaxAge;
        this.maxPartitionFetchBytes = maxPartitionFetchBytes;
        this.sendBuffer = sendBuffer;
        this.receiveBuffer = receiveBuffer;
        this.clientId = clientId;
        this.clientRack = clientRack;
        this.reconnectBackoffMs = reconnectBackoffMs;
        this.retryBackoff = retryBackoff;
        this.metricSampleWindowMs = metricSampleWindowMs;
        this.metricNumSamples = metricNumSamples;
        this.metricsRecordingLevel = metricsRecordingLevel;
        this.checkCrcs = checkCrcs;
        this.connectionsMaxIdleMs = connectionsMaxIdleMs;
        this.requestTimeoutMs = requestTimeoutMs;
        this.defaultApiTimeoutMs = defaultApiTimeoutMs;
        this.excludeInternalTopics = excludeInternalTopics;
        this.defaultExcludeInternalTopics = defaultExcludeInternalTopics;
        this.leaveGroupOnClose = leaveGroupOnClose;
        this.isolationLevel = isolationLevel;
        this.allowAutoCreateTopics = allowAutoCreateTopics;
    }
    
    // =========================================================================
    // OVERRIDES
    // =========================================================================
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("KafkaConfig [bootstrapServers=");
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
        builder.append("]");
        return builder.toString();
    }
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
    
    public String getBootstrapServers() {
        return bootstrapServers;
    }
    
    public String getGroupId() {
        return groupId;
    }
    
    public Class<?> getKeyDeserializer() {
        return keyDeserializer;
    }
    
    public Class<?> getValueDeserializer() {
        return valueDeserializer;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public long getTimeout() {
        return timeout;
    }
    
    /* OPTIONAL */
    public String getGroupInstanceId() {
        return groupInstanceId;
    }
    
    public Integer getMaxPoolRecords() {
        return maxPoolRecords;
    }
    
    public Integer getMaxPoolInterval() {
        return maxPoolInterval;
    }
    
    public Integer getSessionTimeout() {
        return sessionTimeout;
    }
    
    public Integer getHeartBeatMs() {
        return heartBeatMs;
    }
    
    public Boolean getEnableAutoCommit() {
        return enableAutoCommit;
    }
    
    public Integer getAutoComitIntervalMs() {
        return autoComitIntervalMs;
    }
    
    public String getPartitionAssignmentStrategy() {
        return partitionAssignmentStrategy;
    }
    
    public String getAutoOffsetRest() {
        return autoOffsetRest;
    }
    
    public Integer getFetchMinBytes() {
        return fetchMinBytes;
    }
    
    public Integer getFetchMaxBytes() {
        return fetchMaxBytes;
    }
    
    public Integer getFetchMaxWaitMs() {
        return fetchMaxWaitMs;
    }
    
    public Long getMetadataMaxAge() {
        return metadataMaxAge;
    }
    
    public Integer getMaxPartitionFetchBytes() {
        return maxPartitionFetchBytes;
    }
    
    public Integer getSendBuffer() {
        return sendBuffer;
    }
    
    public Integer getReceiveBuffer() {
        return receiveBuffer;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public String getClientRack() {
        return clientRack;
    }
    
    public Long getReconnectBackoffMs() {
        return reconnectBackoffMs;
    }
    
    public Long getRetryBackoff() {
        return retryBackoff;
    }
    
    public Long getMetricSampleWindowMs() {
        return metricSampleWindowMs;
    }
    
    public Long getMetricNumSamples() {
        return metricNumSamples;
    }
    
    public String getMetricsRecordingLevel() {
        return metricsRecordingLevel;
    }
    
    public Boolean getCheckCrcs() {
        return checkCrcs;
    }
    
    public Long getConnectionsMaxIdleMs() {
        return connectionsMaxIdleMs;
    }
    
    public Integer getRequestTimeoutMs() {
        return requestTimeoutMs;
    }
    
    public Integer getDefaultApiTimeoutMs() {
        return defaultApiTimeoutMs;
    }
    
    public Boolean getExcludeInternalTopics() {
        return excludeInternalTopics;
    }
    
    public Boolean getDefaultExcludeInternalTopics() {
        return defaultExcludeInternalTopics;
    }
    
    public Boolean getLeaveGroupOnClose() {
        return leaveGroupOnClose;
    }
    
    public String getIsolationLevel() {
        return isolationLevel;
    }
    
    public Boolean getAllowAutoCreateTopics() {
        return allowAutoCreateTopics;
    }
    
}
