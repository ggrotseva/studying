package com.softuni.mvc.controllers;

import com.softuni.mvc.services.CompanyService;
import com.softuni.mvc.services.EmployeeService;
import com.softuni.mvc.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportXMLController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportXMLController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/xml")
    public String index(Model model) {

        boolean importedCompanies = this.companyService.areImported();
        boolean importedProjects = this.projectService.areImported();
        boolean importedEmployees = this.employeeService.areImported();

        boolean[] importStatuses = { importedCompanies, importedProjects, importedEmployees };

        model.addAttribute("areImported", importStatuses);

        return "xml/import-xml";
    }

    @GetMapping("/companies")
    public String viewImportCompanies(Model model) throws IOException {
        String companiesXml = this.companyService.getXmlContent();

        model.addAttribute("companies", companiesXml);

        return "xml/import-companies";
    }

    @PostMapping("/companies")
    public String importCompanies() throws IOException, JAXBException {
        this.companyService.importCompanies();

        return "redirect:/import/xml";
    }

    @GetMapping("/projects")
    public String viewImportProjects(Model model) throws IOException {
        String projectsXml = this.projectService.getXmlContent();

        model.addAttribute("projects", projectsXml);

        return "xml/import-projects";
    }

    @PostMapping("/projects")
    public String importProjects() throws IOException, JAXBException {
        this.projectService.importProjects();

        return "redirect:/import/xml";
    }

    @GetMapping("/employees")
    public String viewImportEmployees(Model model) throws IOException {
        String employeesXml = this.employeeService.getXmlContent();

        model.addAttribute("employees", employeesXml);

        return "xml/import-employees";
    }

    @PostMapping("/employees")
    public String importEmployees() throws IOException, JAXBException {
        this.employeeService.importEmployees();

        return "redirect:/import/xml";
    }

}
