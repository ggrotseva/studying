package com.likebookapp.model.dtos;

import com.likebookapp.model.entity.Post;
import com.likebookapp.model.enums.MoodName;

public class PostViewDTO {

    private Long id;
    private String authorUsername;
    private String content;
    private MoodName mood;
    private int likes;

    public Long getId() {
        return id;
    }

    public PostViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public PostViewDTO setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostViewDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public MoodName getMood() {
        return mood;
    }

    public PostViewDTO setMood(MoodName mood) {
        this.mood = mood;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public PostViewDTO setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public PostViewDTO mapToView(Post post) {
        this.id = post.getId();
        this.authorUsername = post.getAuthor().getUsername();
        this.content = post.getContent();
        this.mood = post.getMood().getMoodName();
        this.likes = post.getLikes().size();
        return this;
    }
}
