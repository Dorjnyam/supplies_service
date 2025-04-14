package mn.clinic.supplies.application.port.out;

import mn.clinic.supplies.domain.model.Supply;
import java.util.List;

public interface SupplyRepositoryPort {
    void save(Supply supply);
    List<Supply> findAll();
}
