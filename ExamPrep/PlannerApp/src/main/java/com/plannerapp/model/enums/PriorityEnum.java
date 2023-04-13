package com.plannerapp.model.enums;

public enum PriorityEnum {
    URGENT("An urgent problem that blocks the system use until the issue is resolved"),
    IMPORTANT("A core functionality that your product is explicitly supposed to perform is compromised"),
    LOW("Should be fixed if time permits but can be postponed");

    private String description;

    PriorityEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
