package softuni.expirationManager.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String brand;

    @Column
    private String description;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CategoryEntity category;

    public ProductEntity() {
    }

    public ProductEntity(String name, String brand,
                         String description, LocalDate expiryDate,
                         CategoryEntity category) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.expiryDate = expiryDate;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public ProductEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public ProductEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ProductEntity setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public ProductEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }
}
