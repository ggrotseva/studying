package com.resellerapp.model.enums;

public enum ConditionEnum {

    EXCELLENT("In perfect condition"),
    GOOD("Some signs of wear and tear or minor defects"),
    ACCEPTABLE("The item is fairly worn but continues to function properly");

    private final String description;

    ConditionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
