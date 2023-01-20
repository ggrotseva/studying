package com.softuni.mvc.models.employee;

import com.softuni.mvc.models.project.ImportProjectDTO;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportEmployeeDTO {

    @XmlElement(name = "first-name")
    @NotNull
    private String firstName;

    @XmlElement(name = "last-name")
    @NotNull
    private String lastName;

    @XmlElement
    @NotNull
    private int age;

    @XmlElement
    @NotNull
    private ImportProjectDTO project;

    public ImportEmployeeDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ImportProjectDTO getProject() {
        return project;
    }

    public void setProject(ImportProjectDTO project) {
        this.project = project;
    }
}
