package org.inugami.plugins.kafka.services;

public class KafkaProducerIT {
    
    // =========================================================================
    // ATTRIBUTES
    // =========================================================================
    
    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    
    // =========================================================================
    // METHODS
    // =========================================================================
    public static void main(final String[] args) throws Exception {
        new KafkaService().runProducer(20);
        
    }
    // =========================================================================
    // OVERRIDES
    // =========================================================================
    
    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================
}