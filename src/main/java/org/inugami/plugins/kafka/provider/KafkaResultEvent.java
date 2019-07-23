package org.inugami.plugins.kafka.provider;

import org.inugami.api.models.events.GenericEvent;
import org.inugami.api.models.events.SimpleEvent;
import org.inugami.api.providers.task.ProviderFutureResult;

public class KafkaResultEvent {
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final SimpleEvent          event;
    
    private final ProviderFutureResult providerResult;
    
    private final String               channel;
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public KafkaResultEvent(final SimpleEvent event, final ProviderFutureResult providerResult, final String channel) {
        super();
        this.event = event;
        this.providerResult = providerResult;
        this.channel = channel;
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
        builder.append(", channel=");
        builder.append(channel);
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
    
    public String getChannel() {
        return channel;
    }
    
}
