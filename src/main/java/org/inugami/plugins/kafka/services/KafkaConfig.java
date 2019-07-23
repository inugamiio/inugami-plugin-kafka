package org.inugami.plugins.kafka.services;

public class KafkaConfig {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final String   bootstrapServers;
    
    private final String   groupId;
    
    private final Class<?> keyDeserializer;
    
    private final Class<?> valueDeserializer;
    
    private final Class<?> keySerializer;
    
    private final Class<?> valueSerializer;
    
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
    
    private final Integer metricNumSamples;
    
    private final String  metricsRecordingLevel;
    
    private final Boolean checkCrcs;
    
    private final Long    connectionsMaxIdleMs;
    
    private final Integer requestTimeoutMs;
    
    private final Integer defaultApiTimeoutMs;
    
    private final Boolean excludeInternalTopics;
    
    private final Boolean leaveGroupOnClose;
    
    private final String  isolationLevel;
    
    private final Boolean allowAutoCreateTopics;
    
    /* PRODUCER */
    private final Integer batchSize;
    
    private final String  acks;
    
    private final Long    lingerMs;
    
    private final Integer deliveryTimeoutMs;
    
    private final Integer maxRequestSize;
    
    private final Long    maxBlockMs;
    
    private final Long    bufferMemory;
    
    private final String  compressionType;
    
    private final Integer maxInFlightRequestsPerConnection;
    
    private final Integer retries;
    
    private final Boolean enableIdempotence;
    
    private final Integer transactionTimeout;
    
    private final String  transactionalId;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    /* package */ KafkaConfig(final String bootstrapServers, final String groupId, final Class<?> keyDeserializer,
                              final Class<?> valueDeserializer, final Class<?> keySerializer,
                              final Class<?> valueSerializer, final String topic, final long timeout,
                              final String groupInstanceId, final Integer maxPoolRecords, final Integer maxPoolInterval,
                              final Integer sessionTimeout, final Integer heartBeatMs, final Boolean enableAutoCommit,
                              final Integer autoComitIntervalMs, final String partitionAssignmentStrategy,
                              final String autoOffsetRest, final Integer fetchMinBytes, final Integer fetchMaxBytes,
                              final Integer fetchMaxWaitMs, final Long metadataMaxAge,
                              final Integer maxPartitionFetchBytes, final Integer sendBuffer,
                              final Integer receiveBuffer, final String clientId, final String clientRack,
                              final Long reconnectBackoffMs, final Long retryBackoff, final Long metricSampleWindowMs,
                              final Integer metricNumSamples, final String metricsRecordingLevel,
                              final Boolean checkCrcs, final Long connectionsMaxIdleMs, final Integer requestTimeoutMs,
                              final Integer defaultApiTimeoutMs, final Boolean excludeInternalTopics,
                              final Boolean leaveGroupOnClose, final String isolationLevel,
                              final Boolean allowAutoCreateTopics, final Integer batchSize, final String acks,
                              final Long lingerMs, final Integer deliveryTimeoutMs, final Integer maxRequestSize,
                              final Long maxBlockMs, final Long bufferMemory, final String compressionType,
                              final Integer maxInFlightRequestsPerConnection, final Integer retries,
                              final Boolean enableIdempotence, final Integer transactionTimeout,
                              final String transactionalId) {
        super();
        this.bootstrapServers = bootstrapServers;
        this.groupId = groupId;
        this.keyDeserializer = keyDeserializer;
        this.valueDeserializer = valueDeserializer;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
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
        this.leaveGroupOnClose = leaveGroupOnClose;
        this.isolationLevel = isolationLevel;
        this.allowAutoCreateTopics = allowAutoCreateTopics;
        this.batchSize = batchSize;
        this.acks = acks;
        this.lingerMs = lingerMs;
        this.deliveryTimeoutMs = deliveryTimeoutMs;
        this.maxRequestSize = maxRequestSize;
        this.maxBlockMs = maxBlockMs;
        this.bufferMemory = bufferMemory;
        this.compressionType = compressionType;
        this.maxInFlightRequestsPerConnection = maxInFlightRequestsPerConnection;
        this.retries = retries;
        this.enableIdempotence = enableIdempotence;
        this.transactionTimeout = transactionTimeout;
        this.transactionalId = transactionalId;
    }
    
    // =========================================================================
    // OVERRIDES
    // =========================================================================
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("KafkaConfig [bootstrapServers=");
        builder.append(bootstrapServers);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", keyDeserializer=");
        builder.append(keyDeserializer);
        builder.append(", valueDeserializer=");
        builder.append(valueDeserializer);
        builder.append(", keySerializer=");
        builder.append(keySerializer);
        builder.append(", valueSerializer=");
        builder.append(valueSerializer);
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
    
    public String getGroupId() {
        return groupId;
    }
    
    public Class<?> getKeyDeserializer() {
        return keyDeserializer;
    }
    
    public Class<?> getValueDeserializer() {
        return valueDeserializer;
    }
    
    public Class<?> getKeySerializer() {
        return keySerializer;
    }
    
    public Class<?> getValueSerializer() {
        return valueSerializer;
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
    
    public Integer getMetricNumSamples() {
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
    
    public Boolean getLeaveGroupOnClose() {
        return leaveGroupOnClose;
    }
    
    public String getIsolationLevel() {
        return isolationLevel;
    }
    
    public Boolean getAllowAutoCreateTopics() {
        return allowAutoCreateTopics;
    }
    
    public Integer getBatchSize() {
        return batchSize;
    }
    
    public String getAcks() {
        return acks;
    }
    
    public Long getLingerMs() {
        return lingerMs;
    }
    
    public Integer getDeliveryTimeoutMs() {
        return deliveryTimeoutMs;
    }
    
    public Integer getMaxRequestSize() {
        return maxRequestSize;
    }
    
    public Long getMaxBlockMs() {
        return maxBlockMs;
    }
    
    public Long getBufferMemory() {
        return bufferMemory;
    }
    
    public String getCompressionType() {
        return compressionType;
    }
    
    public Integer getMaxInFlightRequestsPerConnection() {
        return maxInFlightRequestsPerConnection;
    }
    
    public Integer getRetries() {
        return retries;
    }
    
    public Boolean getEnableIdempotence() {
        return enableIdempotence;
    }
    
    public Integer getTransactionTimeout() {
        return transactionTimeout;
    }
    
    public String getTransactionalId() {
        return transactionalId;
    }
    
}
