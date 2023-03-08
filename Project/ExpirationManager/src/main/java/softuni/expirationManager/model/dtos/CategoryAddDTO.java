package softuni.expirationManager.model.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class CategoryAddDTO {

    @Size(min = 2, max = 50, message = "Name should be between 2 and 20 characters long")
    @NotNull(message = "Category Name is required")
    private String name;

    @Size(max = 255, message = "Description too long")
    private String description;

    private MultipartFile icon;

    public String getName() {
        return name;
    }

    public CategoryAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getIcon() {
        return icon;
    }

    public CategoryAddDTO setIcon(MultipartFile icon) {
        this.icon = icon;
        return this;
    }
}
