package mn.clinic.supplies.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataSupplyRepository extends JpaRepository<SupplyJpaEntity, Long> {
    Page<SupplyJpaEntity> findByNameContaining(String name, Pageable pageable);
    Page<SupplyJpaEntity> findBySupplierContaining(String supplier, Pageable pageable);
    Page<SupplyJpaEntity> findByExpiryDate(String expiryDate, Pageable pageable);
    List<SupplyJpaEntity> findByNameContainingOrSupplierContaining(String name, String supplier);
    List<SupplyJpaEntity> findByNameContainingIgnoreCaseOrSupplierContainingIgnoreCase(String name, String supplier);

}
