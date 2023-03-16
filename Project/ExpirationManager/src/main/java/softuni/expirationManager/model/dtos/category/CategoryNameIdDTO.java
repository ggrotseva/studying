package softuni.expirationManager.model.dtos.category;

public class CategoryNameIdDTO {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public CategoryNameIdDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryNameIdDTO setName(String name) {
        this.name = name;
        return this;
    }
}
