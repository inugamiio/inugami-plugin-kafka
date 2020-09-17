package io.inugami.plugins.kafka.provider;

import io.inugami.api.ctx.BootstrapContext;
import io.inugami.api.exceptions.services.ProviderException;
import io.inugami.api.models.Gav;
import io.inugami.api.models.data.basic.JsonObject;
import io.inugami.api.models.events.SimpleEvent;
import io.inugami.api.processors.ClassBehavior;
import io.inugami.api.processors.ConfigHandler;
import io.inugami.api.providers.AbstractProvider;
import io.inugami.api.providers.Provider;
import io.inugami.api.providers.ProviderRunner;
import io.inugami.api.providers.ProviderWriter;
import io.inugami.api.providers.concurrent.FutureData;
import io.inugami.api.providers.task.ProviderFutureResult;
import io.inugami.api.spi.SpiLoader;
import io.inugami.commons.providers.MockJsonHelper;
import io.inugami.plugins.kafka.services.KafkaConfig;
import io.inugami.plugins.kafka.services.KafkaConfigBuilder;
import io.inugami.plugins.kafka.services.KafkaService;

import java.util.List;

public class KafkaProvider extends AbstractProvider implements Provider, ProviderWriter, BootstrapContext<Object> {

    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final String name;

    private final KafkaService kafkaService;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public KafkaProvider(final ClassBehavior classBehavior, final ConfigHandler<String, String> config,
                         final ProviderRunner providerRunner) {
        super(classBehavior, config, providerRunner);
        this.name = classBehavior.getName();

        final SpiLoader      spiLoader           = new SpiLoader();
        final String         providerHandlerName = config.optionnal().grab("providerHandler");
        KafkaProviderHandler providerHandler     = null;
        if (providerHandlerName != null) {
            providerHandler = spiLoader.loadSpiService(providerHandlerName, KafkaProviderHandler.class);
        }
        if (providerHandler == null) {
            providerHandler = new DefaultKafkaProviderHandler(config.grabBoolean("escapeJson", false));
        }

        final String      defaultChannel = config.grabOrDefault("defaultChannel", "globale");
        final boolean     enableConsumer = config.grabBoolean("enableConsumer", true);
        final KafkaConfig configKafka    = KafkaConfigBuilder.buildConfig(this.name, config);
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
