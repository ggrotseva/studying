package softuni.expirationManager.model.dtos.product;

import java.time.LocalDate;

public class ProductViewDTO {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private LocalDate expiryDate;
    private String categoryUserUsername;

    public Long getId() {
        return id;
    }

    public ProductViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductViewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public ProductViewDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductViewDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ProductViewDTO setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public String getCategoryUserUsername() {
        return categoryUserUsername;
    }

    public ProductViewDTO setCategoryUserUsername(String categoryUserUsername) {
        this.categoryUserUsername = categoryUserUsername;
        return this;
    }
}
