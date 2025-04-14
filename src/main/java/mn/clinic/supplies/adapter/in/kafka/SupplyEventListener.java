package mn.clinic.supplies.adapter.in.kafka;

import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.domain.model.Supply;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SupplyEventListener {

    private final ManageSupplyUseCase supplyUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SupplyEventListener(ManageSupplyUseCase supplyUseCase) {
        this.supplyUseCase = supplyUseCase;
    }

    @KafkaListener(topics = "new-supplies", groupId = "clinic-supply-consumer")
    public void listen(String message) throws Exception {
        Supply supply = objectMapper.readValue(message, Supply.class);
        supplyUseCase.addSupply(supply);
        System.out.println("✅ Received and saved new supply from Kafka: " + supply.getName());
    }
}
