package com.softuni.mvc.controllers;

import com.softuni.mvc.services.EmployeeService;
import com.softuni.mvc.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/export")
public class ExportController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/project-if-finished")
    public String exportFinalizedProjects(Model model) {
        String projectsIfFinished = this.projectService.exportFinalizedProjects();

        model.addAttribute("projectsIfFinished", projectsIfFinished);

        return "export/export-project-if-finished";
    }

    @GetMapping("/employees-above")
    public String exportEmployeesWithAgeAbove25(Model model) {
        String employeesAbove = this.employeeService.exportEmployeesWithAgeAbove();

        model.addAttribute("employeesAbove", employeesAbove);

        return "export/export-employees-with-age";
    }

}
