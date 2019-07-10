package org.inugami.plugins.kafka.provider;

import java.util.List;

import org.inugami.api.exceptions.services.ProviderException;
import org.inugami.api.models.Gav;
import org.inugami.api.models.events.SimpleEvent;
import org.inugami.api.processors.ClassBehavior;
import org.inugami.api.processors.ConfigHandler;
import org.inugami.api.providers.AbstractProvider;
import org.inugami.api.providers.Provider;
import org.inugami.api.providers.ProviderRunner;
import org.inugami.api.providers.concurrent.FutureData;
import org.inugami.api.providers.task.ProviderFutureResult;

public class KafkaProvider extends AbstractProvider implements Provider {
    
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final String name;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public KafkaProvider(final ClassBehavior classBehavior, final ConfigHandler<String, String> config,
                         final ProviderRunner providerRunner) {
        super(classBehavior, config, providerRunner);
        this.name = classBehavior.getName();
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
        // TODO Auto-generated method stub
        return null;
    }
    
    // =========================================================================
    // OVERRIDES
    // =========================================================================
    
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
    @Override
    public String getType() {
        return null;
    }
}
