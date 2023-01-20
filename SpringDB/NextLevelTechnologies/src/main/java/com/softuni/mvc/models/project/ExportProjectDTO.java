package com.softuni.mvc.models.project;

import java.math.BigDecimal;

public class ExportProjectDTO {

    private String name;

    private String description;

    private BigDecimal payment;

    public ExportProjectDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return String.format("Project Name: %s%n    Description: %s%n   %.2f",
                this.name, this.description, this.payment);
    }
}
