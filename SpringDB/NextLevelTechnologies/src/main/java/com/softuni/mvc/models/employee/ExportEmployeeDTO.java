package com.softuni.mvc.models.employee;

public class ExportEmployeeDTO {

    private String firstName;

    private String lastName;

    private int age;

    private String projectName;

    public ExportEmployeeDTO() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s%n   Age: %d%n   Project Name: %s",
                firstName, lastName, age,  projectName);
    }
}
