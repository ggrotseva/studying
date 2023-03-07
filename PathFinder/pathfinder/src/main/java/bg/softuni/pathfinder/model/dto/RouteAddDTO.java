package bg.softuni.pathfinder.model.dto;

import bg.softuni.pathfinder.model.enums.Level;
import bg.softuni.pathfinder.model.enums.RouteCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

public class RouteAddDTO {

    @NotNull
    @Size(min = 5, max = 20)
    private String name;

    @NotNull
    @Size(min = 5)
    private String description;

    @NotNull
    private MultipartFile gpxCoordinates;

    @NotNull
    private Level level;

    @NotEmpty
    private String videoUrl;

    @NotNull
    private Set<RouteCategory> categories;

    public RouteAddDTO() {
        this.categories = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public RouteAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RouteAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getGpxCoordinates() {
        return gpxCoordinates;
    }

    public RouteAddDTO setGpxCoordinates(MultipartFile gpxCoordinates) {
        this.gpxCoordinates = gpxCoordinates;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    public RouteAddDTO setLevel(Level level) {
        this.level = level;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public RouteAddDTO setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Set<RouteCategory> getCategories() {
        return categories;
    }

    public RouteAddDTO setCategories(Set<RouteCategory> categories) {
        this.categories = categories;
        return this;
    }
}
