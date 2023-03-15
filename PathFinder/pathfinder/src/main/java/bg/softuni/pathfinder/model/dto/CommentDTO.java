package bg.softuni.pathfinder.model.dto;

import java.time.LocalDateTime;

public class CommentDTO {

    private Long id;
    private boolean approved;
    private LocalDateTime created;
    private String textContent;
    private String authorUsername;

    public Long getId() {
        return id;
    }

    public CommentDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isApproved() {
        return approved;
    }

    public CommentDTO setApproved(boolean approved) {
        this.approved = approved;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public CommentDTO setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentDTO setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public CommentDTO setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }
}
