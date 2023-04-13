package com.plannerapp.model.dtos;

import com.plannerapp.model.enums.PriorityEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class TaskAddDTO {

    @NotNull
    @Size(min = 2, max = 50, message = "Description length must be between 2 and 50 characters!")
    private String description;

    @Future(message = "Due date must be in future.")
    @NotNull(message = "Due date cannot be empty, please enter a valid date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @NotNull(message = "You must select a priority!")
    private PriorityEnum priority;

    public String getDescription() {
        return description;
    }

    public TaskAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskAddDTO setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public TaskAddDTO setPriority(PriorityEnum priority) {
        this.priority = priority;
        return this;
    }
}
