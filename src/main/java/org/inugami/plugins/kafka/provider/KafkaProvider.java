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
import org.inugami.commons.spi.SpiLoader;
import org.inugami.plugins.kafka.services.KafkaConfig;
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
        
        final SpiLoader spiLoader = new SpiLoader();
        final String providerHandlerName = config.optionnal().grab("providerHandler");
        KafkaProviderHandler providerHandler = null;
        if (providerHandlerName != null) {
            providerHandler = spiLoader.loadSpiService(providerHandlerName, KafkaProviderHandler.class);
        }
        if (providerHandler == null) {
            providerHandler = new DefaultKafkaProviderHandler(config.grabBoolean("escapeJson", false));
        }
        
        final String defaultChannel = config.grabOrDefault("defaultChannel", "globale");
        final boolean enableConsumer = config.grabBoolean("enableConsumer", true);
        final KafkaConfig configKafka = KafkaConfigBuilder.buildConfig(this.name, config);
        this.kafkaService = new KafkaService(configKafka, getName(), defaultChannel, providerHandler, enableConsumer,
                                             false, null);
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
