package io.inugami.plugins.kafka.provider;

import io.inugami.api.models.events.SimpleEvent;
import io.inugami.api.providers.task.ProviderFutureResult;
import io.inugami.api.spi.NamedSpi;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Arrays;
import java.util.List;

public interface KafkaProviderHandler extends NamedSpi {

    List<KafkaResultEvent> convertToEvents(final String providerName, ConsumerRecord<Long, String> record,
                                           final String defaultChannel);

    default List<KafkaResultEvent> buildSingleResult(final SimpleEvent event, final ProviderFutureResult providerResult,
                                                     final String channel) {
        return Arrays.asList(new KafkaResultEvent(event, providerResult, channel));
    }
}
