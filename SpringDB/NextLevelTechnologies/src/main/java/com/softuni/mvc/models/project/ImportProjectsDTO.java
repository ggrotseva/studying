package com.softuni.mvc.models.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportProjectsDTO {

    @XmlElement(name = "project")
    private List<ImportProjectDTO> projects;

    public ImportProjectsDTO() {
    }

    public List<ImportProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ImportProjectDTO> projects) {
        this.projects = projects;
    }
}
