package mn.clinic.supplies.application.service;

import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.adapter.out.messaging.KafkaMessageProducer; // Fix the import to use the correct class name (KafkaMessageProducer)
import mn.clinic.supplies.application.port.out.SupplyRepositoryPort;
import mn.clinic.supplies.domain.model.Supply;
import java.util.List;

public class SupplyService implements ManageSupplyUseCase {
    private final SupplyRepositoryPort repository;
    private final KafkaMessageProducer kafkaProducer;  // Use KafkaMessageProducer

    // Inject KafkaMessageProducer via constructor
    public SupplyService(SupplyRepositoryPort repository, KafkaMessageProducer kafkaProducer) {
        this.repository = repository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void addSupply(Supply supply) {
        repository.save(supply);  // Save the supply to the repository
        kafkaProducer.sendMessage("new-supplies", supply.toString());  // Send the supply to Kafka
    }

    @Override
    public List<Supply> listSupplies() {
        return repository.findAll();  // Return the list of supplies from the repository
    }
}
