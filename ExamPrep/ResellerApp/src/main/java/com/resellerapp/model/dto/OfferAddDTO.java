package com.resellerapp.model.dto;

import com.resellerapp.model.enums.ConditionEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class OfferAddDTO {

    @NotNull
    @Size(min = 2, max = 50, message = "Description length must be between 2 and 50 characters!")
    private String description;

    @NotNull(message = "You must specify a price")
    @Positive(message = "Price must be positive number!")
    private BigDecimal price;

    @NotNull(message = "You must select a condition!")
    private ConditionEnum condition;

    public String getDescription() {
        return description;
    }

    public OfferAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferAddDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public OfferAddDTO setCondition(ConditionEnum condition) {
        this.condition = condition;
        return this;
    }
}
