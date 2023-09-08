package bg.softuni.pathfinder.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentAddDTO {

    @Size(min = 10)
    @NotBlank
    private String message;

    public String getMessage() {
        return message;
    }

    public CommentAddDTO setMessage(String message) {
        this.message = message;
        return this;
    }
}
