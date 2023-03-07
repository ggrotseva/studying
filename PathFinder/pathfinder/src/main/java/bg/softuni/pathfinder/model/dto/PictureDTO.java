package bg.softuni.pathfinder.model.dto;

public class PictureDTO {

    private Long id;
    private String title;
    private String url;

    public Long getId() {
        return id;
    }

    public PictureDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PictureDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureDTO setUrl(String url) {
        this.url = url;
        return this;
    }
}
