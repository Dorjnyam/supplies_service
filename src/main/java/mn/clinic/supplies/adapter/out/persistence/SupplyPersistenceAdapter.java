package mn.clinic.supplies.adapter.out.persistence;

import mn.clinic.supplies.application.port.out.SupplyRepositoryPort;
import mn.clinic.supplies.domain.model.Supply;
import org.springframework.stereotype.Component;

import java.util.List;
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
}
