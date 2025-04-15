package mn.clinic.supplies.adapter.out.persistence;

import mn.clinic.supplies.application.port.out.SupplyRepositoryPort;
import mn.clinic.supplies.domain.model.Supply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import mn.clinic.supplies.domain.exception.SupplyNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SupplyPersistenceAdapter implements SupplyRepositoryPort {

    private final SpringDataSupplyRepository repository;

    public SupplyPersistenceAdapter(SpringDataSupplyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Supply supply) {
        repository.save(new SupplyJpaEntity(
                supply.getName(),
                supply.getQuantity(),
                supply.getExpiryDate(),
                supply.getSupplier()
        ));
    }

    @Override
    public List<Supply> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Supply(
                        entity.getId(),
                        entity.getName(),
                        entity.getQuantity(),
                        entity.getExpiryDate(),
                        entity.getSupplier()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Supply> findById(Long id) {
        return repository.findById(id).map(entity -> new Supply(
                entity.getId(),
                entity.getName(),
                entity.getQuantity(),
                entity.getExpiryDate(),
                entity.getSupplier()
        ));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);  // Delete the supply by ID
    }

    @Override
    public Page<Supply> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(entity -> new Supply(
                entity.getId(),
                entity.getName(),
                entity.getQuantity(),
                entity.getExpiryDate(),
                entity.getSupplier()
        ));
    }

    @Override
    public Page<Supply> findByNameContaining(Pageable pageable, String name) {
        return repository.findByNameContaining(name, pageable).map(entity -> new Supply(
                entity.getId(),
                entity.getName(),
                entity.getQuantity(),
                entity.getExpiryDate(),
                entity.getSupplier()
        ));
    }

    @Override
    public Page<Supply> findBySupplierContaining(Pageable pageable, String supplier) {
        return repository.findBySupplierContaining(supplier, pageable).map(entity -> new Supply(
                entity.getId(),
                entity.getName(),
                entity.getQuantity(),
                entity.getExpiryDate(),
                entity.getSupplier()
        ));
    }

    @Override
    public Page<Supply> findByExpiryDate(Pageable pageable, String expiryDate) {
        return repository.findByExpiryDate(expiryDate, pageable).map(entity -> new Supply(
                entity.getId(),
                entity.getName(),
                entity.getQuantity(),
                entity.getExpiryDate(),
                entity.getSupplier()
        ));
    }

    @Override
    public List<Supply> searchSupplies(String query) {
        // Search by both name and supplier using the same query string
        return repository.findByNameContainingOrSupplierContaining(query, query).stream()
                .map(entity -> new Supply(
                        entity.getId(),
                        entity.getName(),
                        entity.getQuantity(),
                        entity.getExpiryDate(),
                        entity.getSupplier()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Supply> findByNameContainingOrSupplierContaining(String name, String supplier) {
        return repository.findByNameContainingIgnoreCaseOrSupplierContainingIgnoreCase(name, supplier)
                .stream()
                .map(entity -> new Supply(
                        entity.getId(),
                        entity.getName(),
                        entity.getQuantity(),
                        entity.getExpiryDate(),
                        entity.getSupplier()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Supply updateSupply(Long id, Supply updatedSupply) {
        // Fetch the existing supply
        Optional<SupplyJpaEntity> existingSupplyOptional = repository.findById(id);
        if (existingSupplyOptional.isPresent()) {
            SupplyJpaEntity existingSupply = existingSupplyOptional.get();
            
            // Update the existing entity's fields
            existingSupply.setName(updatedSupply.getName());
            existingSupply.setQuantity(updatedSupply.getQuantity());
            existingSupply.setExpiryDate(updatedSupply.getExpiryDate());
            existingSupply.setSupplier(updatedSupply.getSupplier());

            // Save the updated supply entity
            repository.save(existingSupply);

            // Return the updated Supply
            return new Supply(
                    existingSupply.getId(),
                    existingSupply.getName(),
                    existingSupply.getQuantity(),
                    existingSupply.getExpiryDate(),
                    existingSupply.getSupplier()
            );
        } else {
            // Throw an exception or handle the case when the supply is not found
            throw new SupplyNotFoundException("Supply with ID " + id + " not found.");
        }
    }


}
