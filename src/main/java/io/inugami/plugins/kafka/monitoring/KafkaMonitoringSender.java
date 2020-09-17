/* --------------------------------------------------------------------
 *  Inugami
 * --------------------------------------------------------------------
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.inugami.plugins.kafka.monitoring;

import io.inugami.api.models.data.basic.JsonObject;
import io.inugami.api.monitoring.models.GenericMonitoringModel;
import io.inugami.api.monitoring.senders.MonitoringSender;
import io.inugami.api.monitoring.senders.MonitoringSenderException;
import io.inugami.api.processors.ConfigHandler;
import io.inugami.plugins.kafka.commons.DefaultKafkaProducerHandler;
import io.inugami.plugins.kafka.commons.KafkaProducerHandler;
import io.inugami.plugins.kafka.services.KafkaConfig;
import io.inugami.plugins.kafka.services.KafkaConfigBuilder;
import io.inugami.plugins.kafka.services.KafkaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KafkaMonitoringSender implements MonitoringSender {

    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final KafkaService service;

    private final KafKaMonitoringHandler monitoringHandler;

    private final ConfigHandler<String, String> config;
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================

    public KafkaMonitoringSender() {
        service           = null;
        monitoringHandler = null;
        config            = null;
    }

    public KafkaMonitoringSender(final KafkaService service, final KafKaMonitoringHandler monitoringHandler,
                                 final ConfigHandler<String, String> config) {
        this.service           = service;
        this.monitoringHandler = monitoringHandler;
        this.config            = config;
    }

    @Override
    public MonitoringSender buildInstance(final ConfigHandler<String, String> config) {

        final KafKaMonitoringHandler monitoringHandler = KafkaService.resolveHandler(config, "monitoringHandler",
                                                                                     new DefaultKafkaMonitoringHandler(),
                                                                                     KafKaMonitoringHandler.class);

        final KafkaProducerHandler producerHandler = KafkaService.resolveHandler(config, "producerHandler",
                                                                                 new DefaultKafkaProducerHandler(),
                                                                                 KafkaProducerHandler.class);

        final String      defaultChannel = config.grabOrDefault("defaultChannel", "globale");
        final KafkaConfig configKafka    = KafkaConfigBuilder.buildConfig(getName(), config);
        return new KafkaMonitoringSender(new KafkaService(configKafka, getName(), defaultChannel, null, false, true,
                                                          producerHandler),
                                         monitoringHandler, config);
    }

    // =========================================================================
    // METHODS
    // =========================================================================

    @Override
    public void process(final List<GenericMonitoringModel> data) throws MonitoringSenderException {

        final List<JsonObject> dataToSend = new ArrayList<>();

        for (final GenericMonitoringModel item : Optional.ofNullable(data).orElse(new ArrayList<>())) {
            if (item != null) {
                if (monitoringHandler == null) {
                    dataToSend.add(item);
                }
                else {
                    dataToSend.add(monitoringHandler.convert(item, config));
                }
            }
        }
        service.sendMessageToKafka(dataToSend);

    }
    // =========================================================================
    // OVERRIDES
    // =========================================================================

    @Override
    public void shutdown() {
        if (service != null) {
            service.shutdown(null);
        }
    }

}
