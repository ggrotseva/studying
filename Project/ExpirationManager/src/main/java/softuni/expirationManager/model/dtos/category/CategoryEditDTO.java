package softuni.expirationManager.model.dtos.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import softuni.expirationManager.model.validations.FileSizeValidator;

public class CategoryEditDTO {

    private Long id;

    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters long")
    @NotEmpty(message = "Category Name is required")
    private String name;

    @Size(max = 255, message = "Description should be less than 255 characters")
    private String description;

    @FileSizeValidator(maxSizeInKilobytes = "64")
    private MultipartFile icon;

    public Long getId() {
        return id;
    }

    public CategoryEditDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryEditDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEditDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getIcon() {
        return icon;
    }

    public CategoryEditDTO setIcon(MultipartFile icon) {
        this.icon = icon;
        return this;
    }
}
