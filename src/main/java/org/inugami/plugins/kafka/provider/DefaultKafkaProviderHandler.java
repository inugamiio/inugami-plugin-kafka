package org.inugami.plugins.kafka.provider;

import java.util.Collections;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.inugami.api.loggers.Loggers;
import org.inugami.api.models.data.basic.StringJson;
import org.inugami.api.models.events.SimpleEvent;
import org.inugami.api.models.events.SimpleEventBuilder;
import org.inugami.api.providers.task.ProviderFutureResult;
import org.inugami.api.providers.task.ProviderFutureResultBuilder;

public class DefaultKafkaProviderHandler implements KafkaProviderHandler {
    
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private boolean grabBoolean;
    
    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================
    public DefaultKafkaProviderHandler(boolean grabBoolean) {
        this.grabBoolean = grabBoolean;
    }
    
    // =========================================================================
    // OVERRIDES
    // =========================================================================
    @Override
    public List<KafkaResultEvent> convertToEvents(final String providerName, final ConsumerRecord<Long, String> record,
                                                  final String defaultChannel) {
        
        if (record.value() == null || record.value().trim().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        SimpleEvent event = buildEvent(providerName);
        
        final ProviderFutureResultBuilder builder = new ProviderFutureResultBuilder();
        builder.addEvent(event);
        builder.addChannel(defaultChannel);
        builder.addData(new StringJson(cleanValue(record.value())));
        
        ProviderFutureResult result = builder.build();
        Loggers.DEBUG.debug("default kafka provider result : {}", result.convertToJson());
        return buildSingleResult(event, result, defaultChannel);
    }
    
    // =========================================================================
    // TOOLS
    // =========================================================================
    private SimpleEvent buildEvent(String providerName) {
        SimpleEventBuilder builder = new SimpleEventBuilder();
        builder.addName(providerName);
        builder.addScheduler("kafka_handler");
        return builder.build();
    }
    
    private String cleanValue(String value) {
        String result = null;
        if (grabBoolean) {
            result = value == null ? null : value.replaceAll("\"", "\\\"");
        }
        else {
            result = value;
        }
        return result;
    }
}
