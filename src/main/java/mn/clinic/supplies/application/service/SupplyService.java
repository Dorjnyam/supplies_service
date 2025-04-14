package mn.clinic.supplies.application.service;

import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.adapter.out.messaging.KafkaMessageProducer; // Fix the import to use the correct class name (KafkaMessageProducer)
import mn.clinic.supplies.application.port.out.SupplyRepositoryPort;
import mn.clinic.supplies.domain.model.Supply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void updateSupply(Supply supply) {
        Optional<Supply> existingSupply = repository.findById(supply.getId());  // Find the supply by ID
        if (existingSupply.isPresent()) {
            Supply updatedSupply = existingSupply.get();
            updatedSupply.setName(supply.getName());
            updatedSupply.setQuantity(supply.getQuantity());
            updatedSupply.setExpiryDate(supply.getExpiryDate());
            updatedSupply.setSupplier(supply.getSupplier());

            repository.save(updatedSupply);  // Save the updated supply
            kafkaProducer.sendMessage("updated-supplies", updatedSupply.toString());  // Send update message to Kafka
        }
    }

    @Override
    public boolean deleteSupply(Long id) {
        Optional<Supply> existingSupply = repository.findById(id);  // Find the supply by ID
        if (existingSupply.isPresent()) {
            repository.deleteById(id);  // Delete the supply
            kafkaProducer.sendMessage("deleted-supplies", "Supply with ID " + id + " deleted.");  // Send deletion message to Kafka
            return true;
        }
        return false;  // Return false if the supply doesn't exist
    }

    @Override
    public Page<Supply> listSuppliesWithPagination(Pageable pageable, String name, String supplier, String expiryDate) {
        if (name != null && !name.isEmpty()) {
            return repository.findByNameContaining(pageable, name);  // Filter by name
        } else if (supplier != null && !supplier.isEmpty()) {
            return repository.findBySupplierContaining(pageable, supplier);  // Filter by supplier
        } else if (expiryDate != null && !expiryDate.isEmpty()) {
            return repository.findByExpiryDate(pageable, expiryDate);  // Filter by expiryDate
        }
        return repository.findAll(pageable);  // Return all supplies with pagination
    }

    @Override
    public Supply findSupplyById(Long id) {
        return repository.findById(id).orElse(null);  // Find a supply by its ID or return null if not found
    }

    @Override
public List<Supply> searchSupplies(String query) {
    return repository.findByNameContainingOrSupplierContaining(query, query);
}


}
