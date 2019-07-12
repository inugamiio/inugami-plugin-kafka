package org.inugami.plugins.kafka.provider;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.inugami.plugins.kafka.KafkaResultEvent;

public class DefaultKafkaProviderHandler implements KafkaProviderHandler {
    
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    
    // =========================================================================
    // OVERRIDES
    // =========================================================================
    @Override
    public List<KafkaResultEvent> convertToEvents(final String providerName,
                                                  final ConsumerRecord<Long, String> record) {
        // TODO Auto-generated method stub
        return null;
    }
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
}
