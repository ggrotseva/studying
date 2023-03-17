package softuni.expirationManager.model.dtos.category;

public class CategoryViewDTO {

    private Long id;
    private String name;
    private String description;
    private String icon;
    private Integer productsCount;

    public Long getId() {
        return id;
    }

    public CategoryViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryViewDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryViewDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public CategoryViewDTO setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public CategoryViewDTO setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
        return this;
    }
}
