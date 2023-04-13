package com.plannerapp.controller;

import com.plannerapp.model.dtos.TaskViewDTO;
import com.plannerapp.service.AuthService;
import com.plannerapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final TaskService taskService;

    @Autowired
    public HomeController(AuthService authService, TaskService taskService) {
        this.authService = authService;
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getIndex() {

        if (this.authService.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model) {

        if (this.authService.isAnonymous()) {
            return "redirect:/users/login";
        }

        List<TaskViewDTO> assignedTasks = this.taskService.getTasksAssignedToCurrentUser();
        List<TaskViewDTO> notAssignedTasks = this.taskService.getNotAssignedTasks();

        model.addAttribute("assignedTasks", assignedTasks);
        model.addAttribute("notAssignedTasks", notAssignedTasks);

        return "home";
    }
}
