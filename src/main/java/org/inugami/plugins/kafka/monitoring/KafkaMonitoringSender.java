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
package org.inugami.plugins.kafka.monitoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.inugami.api.models.data.JsonObject;
import org.inugami.api.monitoring.models.GenericMonitoringModel;
import org.inugami.api.monitoring.senders.MonitoringSender;
import org.inugami.api.monitoring.senders.MonitoringSenderException;
import org.inugami.api.processors.ConfigHandler;
import org.inugami.plugins.kafka.commons.DefaultKafkaProducerHandler;
import org.inugami.plugins.kafka.commons.KafkaProducerHandler;
import org.inugami.plugins.kafka.services.KafkaConfig;
import org.inugami.plugins.kafka.services.KafkaConfigBuilder;
import org.inugami.plugins.kafka.services.KafkaService;

public class KafkaMonitoringSender implements MonitoringSender {
    
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    private final KafkaService                  service;
    
    private final KafKaMonitoringHandler        monitoringHandler;
    
    private final ConfigHandler<String, String> config;
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    
    public KafkaMonitoringSender() {
        service = null;
        monitoringHandler = null;
        config = null;
    }
    
    public KafkaMonitoringSender(KafkaService service, final KafKaMonitoringHandler monitoringHandler,
                                 final ConfigHandler<String, String> config) {
        this.service = service;
        this.monitoringHandler = monitoringHandler;
        this.config = config;
    }
    
    @Override
    public MonitoringSender buildInstance(ConfigHandler<String, String> config) {
        
        final KafKaMonitoringHandler monitoringHandler = KafkaService.resolveHandler(config, "monitoringHandler",
                                                                                     new DefaultKafkaMonitoringHandler(),
                                                                                     KafKaMonitoringHandler.class);
        
        final KafkaProducerHandler producerHandler = KafkaService.resolveHandler(config, "producerHandler",
                                                                                 new DefaultKafkaProducerHandler(),
                                                                                 KafkaProducerHandler.class);
        
        final String defaultChannel = config.grabOrDefault("defaultChannel", "globale");
        final KafkaConfig configKafka = KafkaConfigBuilder.buildConfig(getName(), config);
        return new KafkaMonitoringSender(new KafkaService(configKafka, getName(), defaultChannel, null, false, true,
                                                          producerHandler),
                                         monitoringHandler, config);
    }
    
    // =========================================================================
    // METHODS
    // =========================================================================
    
    @Override
    public void process(List<GenericMonitoringModel> data) throws MonitoringSenderException {
        
        final List<JsonObject> dataToSend = new ArrayList<>();
        
        for (GenericMonitoringModel item : Optional.ofNullable(data).orElse(new ArrayList<>())) {
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
