package mn.clinic.supplies.config;

import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.application.port.out.SupplyRepositoryPort;
import mn.clinic.supplies.adapter.out.messaging.KafkaMessageProducer;  // Ensure correct import
import mn.clinic.supplies.application.service.SupplyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplyServiceConfig {

    @Bean
    public ManageSupplyUseCase manageSupplyUseCase(SupplyRepositoryPort repositoryPort, KafkaMessageProducer kafkaProducer) {
        return new SupplyService(repositoryPort, kafkaProducer);  // Inject both dependencies
    }
}
