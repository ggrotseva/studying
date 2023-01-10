package carDealer.domain.parts;

import carDealer.domain.suppliers.Supplier;

import java.math.BigDecimal;

public class PartImportDTO {

    private String name;
    private BigDecimal price;
    private int quantity;
    private Supplier supplier;

    public PartImportDTO() {
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }
}
