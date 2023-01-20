package com.softuni.mvc.controllers;

import com.softuni.mvc.services.CompanyService;
import com.softuni.mvc.services.EmployeeService;
import com.softuni.mvc.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public HomeController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/home")
    public String home(Model model) {
        boolean areImported = this.companyService.areImported()
                && this.employeeService.areImported()
                && this.projectService.areImported();

        model.addAttribute("areImported", areImported);

        return "home";
    }
}
