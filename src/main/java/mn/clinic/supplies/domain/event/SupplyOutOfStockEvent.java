package mn.clinic.supplies.domain.event;

import mn.clinic.supplies.domain.model.Supply;

public class SupplyOutOfStockEvent {

    private final Long supplyId;
    private final String supplyName;
    private final int quantityLeft;

    public SupplyOutOfStockEvent(Supply supply) {
        this.supplyId = supply.getId();
        this.supplyName = supply.getName();
        this.quantityLeft = supply.getQuantity();
    }

    // Getters for the fields
    public Long getSupplyId() {
        return supplyId;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }
}
