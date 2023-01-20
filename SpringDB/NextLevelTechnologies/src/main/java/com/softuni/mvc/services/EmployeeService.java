package com.softuni.mvc.services;

import com.softuni.mvc.models.employee.Employee;
import com.softuni.mvc.models.employee.ExportEmployeeDTO;
import com.softuni.mvc.models.employee.ImportEmployeesDTO;
import com.softuni.mvc.repositories.EmployeeRepository;
import com.softuni.mvc.repositories.ProjectRepository;
import com.softuni.mvc.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           ProjectRepository projectRepository,
                           ValidationUtil validationUtil,
                           ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    public String getXmlContent() throws IOException {
        return Files.readString(Path.of("src", "main", "resources", "files", "xmls", "employees.xml"));
    }

    public void importEmployees() throws IOException, JAXBException {

        String xmlContent = getXmlContent();
        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext context = JAXBContext.newInstance(ImportEmployeesDTO.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportEmployeesDTO wrapper = (ImportEmployeesDTO) unmarshaller.unmarshal(stream);

        List<Employee> employees = wrapper.getEmployees().stream()
                .filter(this.validationUtil::isValid)
                .map(dto -> this.mapper.map(dto, Employee.class))
                .peek(employee ->
                        employee.setProject(this.projectRepository.findFirstByName(employee.getProject().getName()).get())
                ).collect(Collectors.toList());

        this.employeeRepository.saveAll(employees);
    }

    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    public String exportEmployeesWithAgeAbove() {
        return this.employeeRepository.findAllByAgeGreaterThan(25).orElseThrow(NoSuchElementException::new)
                .stream()
                .map(employee -> this.mapper.map(employee, ExportEmployeeDTO.class))
                .map(ExportEmployeeDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
