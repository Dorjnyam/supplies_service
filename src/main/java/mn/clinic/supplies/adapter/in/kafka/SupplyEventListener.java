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

    // Listener for new supplies (add supply)
    @KafkaListener(topics = "new-supplies", groupId = "clinic-supply-consumer")
    public void listenNewSupply(String message) throws Exception {
        Supply supply = objectMapper.readValue(message, Supply.class);
        supplyUseCase.addSupply(supply);
        System.out.println("✅ Received and saved new supply from Kafka: " + supply.getName());
    }

    // Listener for updating supplies (update supply)
    @KafkaListener(topics = "update-supplies", groupId = "clinic-supply-consumer")
    public void listenUpdateSupply(String message) throws Exception {
        Supply supply = objectMapper.readValue(message, Supply.class);
        supplyUseCase.updateSupply(supply);
        System.out.println("🔄 Updated supply with ID: " + supply.getId());
    }

    // Listener for deleting supplies (delete supply)
    @KafkaListener(topics = "delete-supplies", groupId = "clinic-supply-consumer")
    public void listenDeleteSupply(Long message) throws Exception {
        boolean isDeleted = supplyUseCase.deleteSupply(message);
        if (isDeleted) {
            System.out.println("🔴 Deleted supply with ID: " + message);
        } else {
            System.out.println("⚠️ Failed to delete supply with ID: " + message + " (not found)");
        }
    }
}
