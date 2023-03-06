package bg.softuni.pathfinder.model.dto;

import bg.softuni.pathfinder.model.entities.Route;

public class RouteMostCommentedDTO {

    private Long id;
    private String name;
    private String description;
    private String pictureUrl;

    public RouteMostCommentedDTO(Long id, String name, String description, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
    }

    public Long getId() {
        return id;
    }

    public RouteMostCommentedDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RouteMostCommentedDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RouteMostCommentedDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public RouteMostCommentedDTO setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public static RouteMostCommentedDTO makeDTO(Route route) {
        return new RouteMostCommentedDTO(
                route.getId(),
                route.getName(),
                route.getDescription(),
                route.getPictures().stream().findFirst().get().getUrl());
    }
}
