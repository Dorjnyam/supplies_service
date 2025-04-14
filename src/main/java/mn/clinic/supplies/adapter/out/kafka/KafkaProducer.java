package mn.clinic.supplies.adapter.out.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaProducer {

    private final org.apache.kafka.clients.producer.KafkaProducer<String, String> producer;

    public KafkaProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");  // Make sure this is correct
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());
        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
    }

    public void sendMessage(String message) {
        producer.send(new ProducerRecord<>("new-supplies", message), (metadata, exception) -> {
            if (exception != null) {
                System.err.println("Error sending message: " + exception.getMessage());
            } else {
                System.out.println("✅ Sent message: " + message);
            }
        });
    }

    public void close() {
        producer.close();
    }
}
