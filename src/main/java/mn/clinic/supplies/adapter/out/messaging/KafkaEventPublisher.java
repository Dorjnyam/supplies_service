package mn.clinic.supplies.adapter.out.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mn.clinic.supplies.application.port.out.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(KafkaEventPublisher.class);
    
    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void publishEvent(String event) {
        kafkaTemplate.send("supply-events", event);
    }

    @Override
    public void publishDomainEvent(Object event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("supply-events", json);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize domain event", e);
        }
    }
}
