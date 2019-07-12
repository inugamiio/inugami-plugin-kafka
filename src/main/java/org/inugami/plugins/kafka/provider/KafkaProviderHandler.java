package org.inugami.plugins.kafka.provider;

import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.inugami.api.models.events.SimpleEvent;
import org.inugami.api.providers.task.ProviderFutureResult;
import org.inugami.api.spi.NamedSpi;

public interface KafkaProviderHandler extends NamedSpi {
    
    List<KafkaResultEvent> convertToEvents(final String providerName, ConsumerRecord<Long, String> record,
                                           final String defaultChannel);
    
    default List<KafkaResultEvent> buildSingleResult(final SimpleEvent event, final ProviderFutureResult providerResult,
                                                     final String channel) {
        return Arrays.asList(new KafkaResultEvent(event, providerResult, channel));
    }
}
