package mn.clinic.supplies.domain.model;

import java.time.LocalDate;

public class Supply {

    private Long id;
    private String name;
    private int quantity;
    private LocalDate expiryDate;
    private String supplier;

    public Supply() {
    }

    public Supply(Long id, String name, int quantity, LocalDate expiryDate, String supplier) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }

    public Supply(String name, int quantity, LocalDate expiryDate, String supplier) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
