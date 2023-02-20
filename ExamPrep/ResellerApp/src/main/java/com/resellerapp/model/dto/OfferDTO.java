package com.resellerapp.model.dto;

import com.resellerapp.model.enums.ConditionEnum;

import java.math.BigDecimal;

public class OfferDTO {

    private Long id;
    private String description;
    private BigDecimal price;
    private ConditionEnum conditionName;
    private String sellerUsername;

    public Long getId() {
        return id;
    }

    public OfferDTO setId(Long id) {
        this.id = id;
        return this;
    }

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

    public String getSellerUsername() {
        return sellerUsername;
    }

    public OfferDTO setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
        return this;
    }
}
