package mn.clinic.supplies.adapter.out.messaging;

import mn.clinic.supplies.application.port.out.EventPublisher;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishEvent(String event) {
        kafkaTemplate.send("supply-events", event);
    }
}
