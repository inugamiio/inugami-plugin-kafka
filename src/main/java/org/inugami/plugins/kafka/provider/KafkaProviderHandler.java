package org.inugami.plugins.kafka.provider;

import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.inugami.api.models.events.GenericEvent;
import org.inugami.api.providers.task.ProviderFutureResult;
import org.inugami.api.spi.NamedSpi;
import org.inugami.plugins.kafka.KafkaResultEvent;

public interface KafkaProviderHandler extends NamedSpi {
    
    List<KafkaResultEvent> convertToEvents(final String providerName, ConsumerRecord<Long, String> record);
    
    default List<KafkaResultEvent> buildSingleResult(final GenericEvent event,
                                                     final ProviderFutureResult providerResult) {
        return Arrays.asList(new KafkaResultEvent(event, providerResult));
    }
}
