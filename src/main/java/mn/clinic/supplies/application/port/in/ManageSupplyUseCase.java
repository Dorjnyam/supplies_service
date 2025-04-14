package mn.clinic.supplies.application.port.in;

import mn.clinic.supplies.domain.model.Supply;
import java.util.List;

public interface ManageSupplyUseCase {
    void addSupply(Supply supply);
    List<Supply> listSupplies();
}
