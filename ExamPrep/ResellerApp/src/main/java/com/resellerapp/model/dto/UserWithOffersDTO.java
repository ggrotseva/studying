package com.resellerapp.model.dto;

import java.util.List;

public class UserWithOffersDTO {

    private String username;

    private List<OfferDTO> offers;

    private List<OfferDTO> boughtOffers;

    public String getUsername() {
        return username;
    }

    public UserWithOffersDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public List<OfferDTO> getOffers() {
        return offers;
    }

    public UserWithOffersDTO setOffers(List<OfferDTO> offers) {
        this.offers = offers;
        return this;
    }

    public List<OfferDTO> getBoughtOffers() {
        return boughtOffers;
    }

    public UserWithOffersDTO setBoughtOffers(List<OfferDTO> boughtOffers) {
        this.boughtOffers = boughtOffers;
        return this;
    }
}
