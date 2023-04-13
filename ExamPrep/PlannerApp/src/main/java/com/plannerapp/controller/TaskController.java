package com.plannerapp.controller;

import com.plannerapp.model.dtos.TaskAddDTO;
import com.plannerapp.service.AuthService;
import com.plannerapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TaskController {

    private final AuthService authService;
    private final TaskService taskService;

    @Autowired
    public TaskController(AuthService authService, TaskService taskService) {
        this.authService = authService;
        this.taskService = taskService;
    }

    @GetMapping("/tasks/add")
    public String getAddTask(Model model) {

        if (this.authService.isAnonymous()) {
            return "redirect:/users/login";
        }

        if (!model.containsAttribute("taskAddDTO")) {
            model.addAttribute("taskAddDTO", new TaskAddDTO());
        }

        return "task-add";
    }

    @PostMapping("/tasks/add")
    public String postAddTask(@Valid TaskAddDTO taskAddDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (this.authService.isAnonymous()) {
            return "redirect:/users/login";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("taskAddDTO", taskAddDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.taskAddDTO", bindingResult);

            return "redirect:/tasks/add";
        }

        this.taskService.addTask(taskAddDTO);

        return "redirect:/home";
    }

    @GetMapping("/tasks/{id}/finish")
    public String finishTask(@PathVariable Long id) {

        if (this.authService.isAnonymous()) {
            return "redirect:/users/login";
        }

        this.taskService.finishTask(id);

        return "redirect:/home";
    }

    @GetMapping("/tasks/{id}/return")
    public String returnTask(@PathVariable Long id) {

        if (this.authService.isAnonymous()) {
            return "redirect:/users/login";
        }

        this.taskService.returnTask(id);

        return "redirect:/home";
    }

    @GetMapping("/tasks/{id}/assign")
    public String assignTask(@PathVariable Long id) {

        if (this.authService.isAnonymous()) {
            return "redirect:/users/login";
        }

        this.taskService.assignTaskToCurrentUser(id);

        return "redirect:/home";
    }

}
