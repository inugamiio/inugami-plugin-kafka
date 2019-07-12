package org.inugami.plugins.kafka;

import org.inugami.api.models.events.GenericEvent;
import org.inugami.api.providers.task.ProviderFutureResult;

public class KafkaResultEvent {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final GenericEvent         event;
    
    private final ProviderFutureResult providerResult;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public KafkaResultEvent(final GenericEvent event, final ProviderFutureResult providerResult) {
        super();
        this.event = event;
        this.providerResult = providerResult;
    }
    
    // =========================================================================
    // OVERRIDES
    // =========================================================================
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("KafkaResultEvent [event=");
        builder.append(event);
        builder.append(", providerResult=");
        builder.append(providerResult);
        builder.append("]");
        return builder.toString();
    }
    
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
    public GenericEvent getEvent() {
        return event;
    }
    
    public ProviderFutureResult getProviderResult() {
        return providerResult;
    }
}
