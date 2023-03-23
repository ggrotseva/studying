package softuni.expirationManager.model.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ProductAddDTO {

    @NotBlank(message = "Product Name is required")
    private String name;

    private String brand;

    private String description;

    @NotNull(message = "Expiry Date is required")
    private LocalDate expiryDate;

    public String getName() {
        return name;
    }

    public ProductAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public ProductAddDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ProductAddDTO setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }
}
