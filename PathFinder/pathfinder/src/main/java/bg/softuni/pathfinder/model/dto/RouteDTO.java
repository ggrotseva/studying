package bg.softuni.pathfinder.model.dto;

import bg.softuni.pathfinder.model.enums.Level;

import java.util.Set;

public class RouteDTO {

    private Long id;
    private String gpxCoordinates;
    private String name;
    private Level level;
    private String authorUsername;
    private String videoUrl;
    private String description;
    private Set<CommentDTO> comments;
    private Set<PictureDTO> pictures;

    public Long getId() {
        return id;
    }

    public RouteDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getGpxCoordinates() {
        return gpxCoordinates;
    }

    public RouteDTO setGpxCoordinates(String gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
        return this;
    }

    public String getName() {
        return name;
    }

    public RouteDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    public RouteDTO setLevel(Level level) {
        this.level = level;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public RouteDTO setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public RouteDTO setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RouteDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public RouteDTO setComments(Set<CommentDTO> comments) {
        this.comments = comments;
        return this;
    }

    public Set<PictureDTO> getPictures() {
        return pictures;
    }

    public RouteDTO setPictures(Set<PictureDTO> pictures) {
        this.pictures = pictures;
        return this;
    }
}
