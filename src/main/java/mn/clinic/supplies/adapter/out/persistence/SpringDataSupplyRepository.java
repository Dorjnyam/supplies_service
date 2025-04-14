package mn.clinic.supplies.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSupplyRepository extends JpaRepository<SupplyJpaEntity, Long> {}
