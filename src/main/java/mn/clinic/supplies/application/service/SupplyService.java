package mn.clinic.supplies.application.service;

import mn.clinic.supplies.application.port.in.ManageSupplyUseCase;
import mn.clinic.supplies.application.port.out.SupplyRepositoryPort;
import mn.clinic.supplies.domain.model.Supply;
import java.util.List;

public class SupplyService implements ManageSupplyUseCase {
    private final SupplyRepositoryPort repository;

    public SupplyService(SupplyRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void addSupply(Supply supply) {
        repository.save(supply);
    }

    @Override
    public List<Supply> listSupplies() {
        return repository.findAll();
    }
}
