package mn.clinic.supplies.domain.model;

import java.time.LocalDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;

public class Supply {

    private Long id;

    @NotNull(message = "Supply name cannot be null")
    private String name;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
    
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
    
    @NotNull(message = "Supplier cannot be null")
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
