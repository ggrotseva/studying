package com.likebookapp.model.entity;

import com.likebookapp.model.enums.MoodName;

import javax.persistence.*;

@Entity
@Table(name = "moods")
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mood_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private MoodName moodName;

    @Column
    private String description;

    public Long getId() {
        return id;
    }

    public Mood setId(Long id) {
        this.id = id;
        return this;
    }

    public MoodName getMoodName() {
        return moodName;
    }

    public Mood setMoodName(MoodName moodName) {
        this.moodName = moodName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Mood setDescription(String description) {
        this.description = description;
        return this;
    }
}
