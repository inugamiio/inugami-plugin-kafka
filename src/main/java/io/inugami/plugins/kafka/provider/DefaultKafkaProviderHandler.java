package io.inugami.plugins.kafka.provider;

import io.inugami.api.loggers.Loggers;
import io.inugami.api.models.data.basic.StringJson;
import io.inugami.api.models.events.SimpleEvent;
import io.inugami.api.models.events.SimpleEventBuilder;
import io.inugami.api.providers.task.ProviderFutureResult;
import io.inugami.api.providers.task.ProviderFutureResultBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Collections;
import java.util.List;

public class DefaultKafkaProviderHandler implements KafkaProviderHandler {

    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final boolean grabBoolean;

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================
    public DefaultKafkaProviderHandler(final boolean grabBoolean) {
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
        final SimpleEvent event = buildEvent(providerName);

        final ProviderFutureResultBuilder builder = new ProviderFutureResultBuilder();
        builder.addEvent(event);
        builder.addChannel(defaultChannel);
        builder.addData(new StringJson(cleanValue(record.value())));

        final ProviderFutureResult result = builder.build();
        Loggers.DEBUG.debug("default kafka provider result : {}", result.convertToJson());
        return buildSingleResult(event, result, defaultChannel);
    }

    // =========================================================================
    // TOOLS
    // =========================================================================
    private SimpleEvent buildEvent(final String providerName) {
        final SimpleEventBuilder builder = new SimpleEventBuilder();
        builder.addName(providerName);
        builder.addScheduler("kafka_handler");
        return builder.build();
    }

    private String cleanValue(final String value) {
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
