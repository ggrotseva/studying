package com.softuni.mvc.services;

import com.softuni.mvc.models.project.ExportProjectDTO;
import com.softuni.mvc.models.project.ImportProjectsDTO;
import com.softuni.mvc.models.project.Project;
import com.softuni.mvc.repositories.CompanyRepository;
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
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          CompanyRepository companyRepository,
                          ValidationUtil validationUtil,
                          ModelMapper mapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    public String getXmlContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xmls/projects.xml"));
    }

    public void importProjects() throws IOException, JAXBException {

        String xmlContent = getXmlContent();
        ByteArrayInputStream stream = new ByteArrayInputStream(xmlContent.getBytes());

        JAXBContext context = JAXBContext.newInstance(ImportProjectsDTO.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportProjectsDTO wrapper = (ImportProjectsDTO) unmarshaller.unmarshal(stream);

        List<Project> projects = wrapper.getProjects().stream()
                .filter(this.validationUtil::isValid)
                .map(dto -> this.mapper.map(dto, Project.class))
                .peek(project ->
                        project.setCompany(this.companyRepository.findFirstByName(project.getCompany().getName()).get())
                ).collect(Collectors.toList());

        this.projectRepository.saveAll(projects);
    }

    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    public String exportFinalizedProjects() {
        return this.projectRepository.findAllByIsFinished(false).orElseThrow(NoSuchElementException::new)
                .stream()
                .map(project -> this.mapper.map(project, ExportProjectDTO.class))
                .map(ExportProjectDTO::toString).collect(Collectors.joining(System.lineSeparator()));
    }
}
