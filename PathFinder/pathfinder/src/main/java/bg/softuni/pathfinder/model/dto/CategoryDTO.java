package bg.softuni.pathfinder.model.dto;

import bg.softuni.pathfinder.model.enums.RouteCategory;

public class CategoryDTO {

    private Long id;
    private RouteCategory name;

    public Long getId() {
        return id;
    }

    public CategoryDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public RouteCategory getName() {
        return name;
    }

    public CategoryDTO setName(RouteCategory name) {
        this.name = name;
        return this;
    }
}
