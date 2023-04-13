package com.plannerapp.service;

import com.plannerapp.model.dtos.TaskAddDTO;
import com.plannerapp.model.dtos.TaskViewDTO;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import com.plannerapp.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AuthService authService;
    private final PriorityService priorityService;

    @Autowired
    public TaskService(TaskRepository taskRepository, AuthService authService, PriorityService priorityService) {
        this.taskRepository = taskRepository;
        this.authService = authService;
        this.priorityService = priorityService;
    }

    public void addTask(TaskAddDTO taskAddDTO) {
        Priority priority = this.priorityService.findByName(taskAddDTO.getPriority());

        Task newTask = new Task()
                .setDescription(taskAddDTO.getDescription())
                .setDueDate(taskAddDTO.getDueDate())
                .setPriority(priority);

        this.taskRepository.saveAndFlush(newTask);
    }

    public List<TaskViewDTO> getTasksAssignedToCurrentUser() {
        return this.taskRepository.findByUserId(this.authService.getLoggedUser().getId())
                .stream().map(t -> new TaskViewDTO().mapFromTask(t))
                .collect(Collectors.toList());
    }

    public List<TaskViewDTO> getNotAssignedTasks() {
        return this.taskRepository.findByUserIsNull()
                .stream().map(t -> new TaskViewDTO().mapFromTask(t))
                .collect(Collectors.toList());
    }

    public void finishTask(Long id) {
        this.taskRepository.deleteById(id);
    }

    public void assignTaskToCurrentUser(Long taskId) {
        Task task = this.taskRepository.findById(taskId).orElseThrow();

        task.setUser(this.authService.getLoggedUser());

        this.taskRepository.saveAndFlush(task);
    }

    public void returnTask(Long taskId) {
        Task task = this.taskRepository.findById(taskId).orElseThrow();

        task.setUser(null);

        this.taskRepository.saveAndFlush(task);
    }
}
