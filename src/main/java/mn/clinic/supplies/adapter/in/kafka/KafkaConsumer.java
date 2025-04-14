package mn.clinic.supplies.adapter.in.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "new-supplies", groupId = "your-group-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
