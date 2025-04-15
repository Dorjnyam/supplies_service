package mn.clinic.supplies.adapter.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.domain.model.Supply;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final ManageSupplyUseCase supplyUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumer(ManageSupplyUseCase supplyUseCase) {
        this.supplyUseCase = supplyUseCase;
    }

    @KafkaListener(topics = "new-supplies", groupId = "your-group-id")
    public void addSupplyListener(String message) {
        Supply supply = parseJson(message);
        supplyUseCase.addSupply(supply);
        System.out.println("🟢 Added supply: " + supply.getName());
    }

    @KafkaListener(topics = "update-supply", groupId = "your-group-id")
    public void updateSupplyListener(String message) {
        Supply updated = parseJson(message);
        Supply existing = supplyUseCase.findSupplyById(updated.getId());

        existing.setName(updated.getName());
        existing.setQuantity(updated.getQuantity());
        existing.setExpiryDate(updated.getExpiryDate());
        existing.setSupplier(updated.getSupplier());

        supplyUseCase.updateSupply(existing);
        System.out.println("🟡 Updated supply: " + existing.getName());
    }

    @KafkaListener(topics = "delete-supply", groupId = "your-group-id")
public void deleteSupplyListener(Long message) {
    supplyUseCase.deleteSupply(message);  // Directly parse and pass the value
    System.out.println("🔴 Deleted supply with ID: " + message);
}


    @KafkaListener(topics = "search-supply", groupId = "your-group-id")
    public void searchSupplyListener(String message) {
        var results = supplyUseCase.searchSupplies(message);
        System.out.println("🔍 Search results for: " + message);
        results.forEach(s -> System.out.println("→ " + s.getName()));
    }

    private Supply parseJson(String json) {
        try {
            return objectMapper.readValue(json, Supply.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("⚠️ JSON processing error while parsing Supply: " + json, e);
        }
    }
    
}
