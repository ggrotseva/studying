package com.plannerapp.model.dtos;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.User;
import com.plannerapp.model.enums.PriorityEnum;

import java.time.LocalDate;

public class TaskViewDTO {

    private Long id;
    private String description;
    private LocalDate dueDate;
    private PriorityEnum priority;
    private String username;

    public Long getId() {
        return id;
    }

    public TaskViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskViewDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskViewDTO setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public TaskViewDTO setPriority(PriorityEnum priority) {
        this.priority = priority;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public TaskViewDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public TaskViewDTO mapFromTask(Task task) {
        return new TaskViewDTO()
                .setId(task.getId())
                .setDescription(task.getDescription())
                .setPriority(task.getPriority().getName())
                .setDueDate(task.getDueDate())
                .setUsername(task.getUser() != null
                        ? task.getUser().getUsername()
                        : null);
    }
}
