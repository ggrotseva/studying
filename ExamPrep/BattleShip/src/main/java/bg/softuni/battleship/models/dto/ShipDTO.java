package bg.softuni.battleship.models.dto;

public class ShipDTO {

    private Long id;

    private String name;

    private Long health;

    private Long power;

    public Long getId() {
        return id;
    }

    public ShipDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShipDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Long getHealth() {
        return health;
    }

    public ShipDTO setHealth(Long health) {
        this.health = health;
        return this;
    }

    public Long getPower() {
        return power;
    }

    public ShipDTO setPower(Long power) {
        this.power = power;
        return this;
    }

    public String getStats() {
        return name + " -- " + health + " -- " + power;
    }
}
