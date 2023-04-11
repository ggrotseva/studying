package com.likebookapp.model.dtos;

import com.likebookapp.model.enums.MoodName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostAddDTO {

    @NotNull
    @Size(min = 2, max = 150, message = "Content length must be between 2 and 150 characters!")
    private String content;

    @NotNull(message = "You must select a mood!")
    private MoodName mood;

    public String getContent() {
        return content;
    }

    public PostAddDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public MoodName getMood() {
        return mood;
    }

    public PostAddDTO setMood(MoodName mood) {
        this.mood = mood;
        return this;
    }
}
