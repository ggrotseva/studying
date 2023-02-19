package com.resellerapp.model.dto;

import com.resellerapp.model.enums.ConditionEnum;

import java.math.BigDecimal;

public class OfferDTO {

    private String description;
    private BigDecimal price;
    private ConditionEnum conditionName;

    public String getDescription() {
        return description;
    }

    public OfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ConditionEnum getConditionName() {
        return conditionName;
    }

    public OfferDTO setConditionName(ConditionEnum conditionName) {
        this.conditionName = conditionName;
        return this;
    }
}
