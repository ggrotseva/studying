package bg.softuni.battleship.models.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CreateShipDTO {

    @NotBlank(message = "The name must not be blank.")
    @Size(min = 2, max = 10, message = "The name must be between 2 and 10 characters long.")
//    @UniqueShipName
    private String name;

    @NotNull(message = "The power cannot be empty, please enter a valid value.")
    @Positive(message = "Power must be a positive value.")
    private Long power;

    @NotNull(message = "The health cannot be empty, please enter a valid value.")
    @Positive(message = "Health must be a positive value.")
    private Long health;

    @PastOrPresent
    @NotNull(message = "The field cannot be empty, please enter a valid date.")
    private LocalDate created;

    @PositiveOrZero(message = "You must specify a category")
    private int category = -1;

    public CreateShipDTO() {
    }

    public String getName() {
        return name;
    }

    public CreateShipDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Long getPower() {
        return power;
    }

    public CreateShipDTO setPower(Long power) {
        this.power = power;
        return this;
    }

    public Long getHealth() {
        return health;
    }

    public CreateShipDTO setHealth(Long health) {
        this.health = health;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public CreateShipDTO setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public int getCategory() {
        return category;
    }

    public CreateShipDTO setCategory(int category) {
        this.category = category;
        return this;
    }
}
