package softuni.expirationManager.model.dtos;

public class CategoryViewDTO {

    private Long id;
    private String name;
    private String description;
    private byte[] icon;
    private String iconBase64;

    public String getIconBase64() {
        return iconBase64;
    }

    public CategoryViewDTO setIconBase64(String iconBase64) {
        this.iconBase64 = iconBase64;
        return this;
    }

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

    public byte[] getIcon() {
        return icon;
    }

    public CategoryViewDTO setIcon(byte[] icon) {
        this.icon = icon;
        return this;
    }
}
