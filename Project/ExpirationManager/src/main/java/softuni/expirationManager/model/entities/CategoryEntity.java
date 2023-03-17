package softuni.expirationManager.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Lob
    @Column
    private byte[] icon;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    public CategoryEntity() {
        this.products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public CategoryEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public byte[] getIcon() {
        return icon;
    }

    public CategoryEntity setIcon(byte[] icon) {
        this.icon = icon;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CategoryEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public List<ProductEntity> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public CategoryEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }

    public void addProduct(ProductEntity product) {
        this.products.add(product);
    }

    public void removeProduct(ProductEntity product) {
        this.products.remove(product);
    }
}
