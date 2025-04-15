package mn.clinic.supplies.application.port.out;

import mn.clinic.supplies.domain.model.Supply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.List;

public interface SupplyRepositoryPort {
    void save(Supply supply);  // Save a supply

    List<Supply> findAll();  // Find all supplies

    Optional<Supply> findById(Long id);  // Find a supply by its ID

    void deleteById(Long id);  // Delete a supply by its ID

    Page<Supply> findAll(Pageable pageable);  // Paginated list of supplies

    Page<Supply> findByNameContaining(Pageable pageable, String name);  // Find supplies by name (partial match)

    Page<Supply> findBySupplierContaining(Pageable pageable, String supplier);  // Find supplies by supplier (partial match)

    Page<Supply> findByExpiryDate(Pageable pageable, String expiryDate);  // Find supplies by expiry date

    // Search by both name and supplier using the same query string
    List<Supply> searchSupplies(String query);

    List<Supply> findByNameContainingOrSupplierContaining(String name, String supplier);

    // New method to update a supply
    Supply updateSupply(Long id, Supply updatedSupply); 

}
