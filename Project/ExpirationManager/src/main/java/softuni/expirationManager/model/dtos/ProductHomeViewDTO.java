package softuni.expirationManager.model.dtos;

import java.time.LocalDate;

public class ProductHomeViewDTO {

    private Long id;
    private String name;
    private String brand;
    private LocalDate expiryDate;
    private Long categoryId;

    public Long getId() {
        return id;
    }

    public ProductHomeViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductHomeViewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public ProductHomeViewDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ProductHomeViewDTO setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ProductHomeViewDTO setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
