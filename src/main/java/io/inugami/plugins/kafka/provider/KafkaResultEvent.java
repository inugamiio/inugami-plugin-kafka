package io.inugami.plugins.kafka.provider;

import io.inugami.api.models.events.SimpleEvent;
import io.inugami.api.providers.task.ProviderFutureResult;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@ToString
@Getter
@RequiredArgsConstructor
public class KafkaResultEvent {

    @EqualsAndHashCode.Include
    private final SimpleEvent event;

    private final ProviderFutureResult providerResult;

    @EqualsAndHashCode.Include
    private final String channel;
}
