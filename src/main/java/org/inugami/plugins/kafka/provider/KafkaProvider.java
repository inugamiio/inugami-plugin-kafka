package org.inugami.plugins.kafka.provider;

import java.util.List;

import org.inugami.api.ctx.BootstrapContext;
import org.inugami.api.exceptions.services.ProviderException;
import org.inugami.api.models.Gav;
import org.inugami.api.models.data.JsonObject;
import org.inugami.api.models.events.SimpleEvent;
import org.inugami.api.processors.ClassBehavior;
import org.inugami.api.processors.ConfigHandler;
import org.inugami.api.providers.AbstractProvider;
import org.inugami.api.providers.Provider;
import org.inugami.api.providers.ProviderRunner;
import org.inugami.api.providers.ProviderWriter;
import org.inugami.api.providers.concurrent.FutureData;
import org.inugami.api.providers.task.ProviderFutureResult;
import org.inugami.commons.providers.MockJsonHelper;
import org.inugami.plugins.kafka.services.KafkaConfigBuilder;
import org.inugami.plugins.kafka.services.KafkaService;

public class KafkaProvider extends AbstractProvider implements Provider, ProviderWriter, BootstrapContext<Object> {
    
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final String       name;
    
    private final KafkaService kafkaService;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public KafkaProvider(final ClassBehavior classBehavior, final ConfigHandler<String, String> config,
                         final ProviderRunner providerRunner) {
        super(classBehavior, config, providerRunner);
        this.name = classBehavior.getName();
        
        final KafkaConfigBuilder builder = new KafkaConfigBuilder();
        builder.setBootstrapServers(config.grabOrDefault("bootstrapServers", "localhost:9092"));
        builder.setTopic(config.grabOrDefault("topic", "test"));
        builder.setGroupId(config.grabOrDefault("groupId", classBehavior.getName()));
        builder.setTimeout(config.grabLong("timeout", 1000));
        
        /* OPTIONAL */
        builder.setGroupInstanceId(config.optionnal().grab("groupeInstanceId"));
        builder.setMaxPoolRecords(config.optionnal().grabInt("maxPoolRecords"));
        builder.setMaxPoolInterval(config.optionnal().grabInt("maxPoolInterval"));
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
        builder.setClientId(config.optionnal().grabOrDefault("clientId", classBehavior.getName()));
        builder.setClientRack(config.optionnal().grab("clientRack"));
        builder.setReconnectBackoffMs(config.optionnal().grabLong("reconnectBackoffMs", 50L));
        builder.setRetryBackoff(config.optionnal().grabLong("retryBackoff", 100L));
        builder.setMetricSampleWindowMs(config.optionnal().grabLong("metricsSampleWindowMs", 30000L));
        builder.setMetricNumSamples(config.optionnal().grabLong("metricsNumSample", 30000L));
        builder.setMetricsRecordingLevel(config.optionnal().grab("metricsRecordingLevel"));
        builder.setCheckCrcs(config.optionnal().grabBoolean("checkCrcs", true));
        builder.setConnectionsMaxIdleMs(config.optionnal().grabLong("connectionsMaxIdleMs", 60000L));
        builder.setRequestTimeoutMs(config.optionnal().grabInt("requestTimeout", 30000));
        builder.setDefaultApiTimeoutMs(config.optionnal().grabInt("defaultApiTimeoutMs", 60000));
        
        builder.setExcludeInternalTopics(config.optionnal().grabBoolean("excludeInternalTopics", true));
        builder.setDefaultExcludeInternalTopics(config.optionnal().grabBoolean("defaultExcludeInternalTopics", true));
        builder.setLeaveGroupOnClose(config.optionnal().grabBoolean("leaveGroupOnClose", true));
        builder.setIsolationLevel(config.optionnal().grab("isolationLevel"));
        builder.setAllowAutoCreateTopics(config.optionnal().grabBoolean("allowAutoCreateTopics", true));
        
        this.kafkaService = new KafkaService(builder.build());
    }
    
    // =========================================================================
    // METHODS
    // =========================================================================
    @Override
    public <T extends SimpleEvent> FutureData<ProviderFutureResult> callEvent(final T event, final Gav pluginGav) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public ProviderFutureResult aggregate(final List<ProviderFutureResult> data) throws ProviderException {
        return MockJsonHelper.aggregate(data);
    }
    
    @Override
    public void write(final JsonObject data) {
        // TODO Auto-generated method stub
        
    }
    
    // =========================================================================
    // BOOTSTRAP CONTEXT
    // =========================================================================
    @Override
    public void bootrap(final Object ctx) {
        kafkaService.bootrap(ctx);
    }
    
    @Override
    public void shutdown(final Object ctx) {
        kafkaService.shutdown(ctx);
    }
    
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
    @Override
    public String getType() {
        return "KAFKA";
    }
    
    @Override
    public String getName() {
        return name;
    }
    
}
