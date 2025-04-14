package mn.clinic.supplies.application.port.in;

import mn.clinic.supplies.domain.model.Supply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManageSupplyUseCase {

    // Add a new supply
    void addSupply(Supply supply);

    // List all supplies (without pagination or filtering)
    List<Supply> listSupplies();

    // Update an existing supply
    void updateSupply(Supply supply);

    // Delete a supply by its ID
    boolean deleteSupply(Long id);

    // Fetch supplies with optional filtering and pagination
    Page<Supply> listSuppliesWithPagination(Pageable pageable, String name, String supplier, String expiryDate);

    // Find a supply by its ID
    Supply findSupplyById(Long id);

    List<Supply> searchSupplies(String query);
}
