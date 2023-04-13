package com.plannerapp.model.entity;

import com.plannerapp.model.enums.PriorityEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityEnum name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "priority")
    private List<Task> tasks;

    public Long getId() {
        return id;
    }

    public Priority setId(Long id) {
        this.id = id;
        return this;
    }

    public PriorityEnum getName() {
        return name;
    }

    public Priority setName(PriorityEnum name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Priority setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Priority setTasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
