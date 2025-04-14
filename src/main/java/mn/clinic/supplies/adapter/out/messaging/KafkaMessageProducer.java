package mn.clinic.supplies.adapter.out.messaging;

import org.apache.kafka.clients.producer.KafkaProducer;  // Import the KafkaProducer class from the Kafka library
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;
import java.util.Properties;

@Component
public class KafkaMessageProducer {  // Renamed class

    private final KafkaProducer<String, String> producer;  // Use the original KafkaProducer from the Kafka library

    public KafkaMessageProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(properties);  // Use the original KafkaProducer
    }

    public void sendMessage(String topic, String message) {
        producer.send(new ProducerRecord<>(topic, message));
        System.out.println("✅ Sent message to Kafka: " + message);
    }

    public void close() {
        producer.close();
    }
}
