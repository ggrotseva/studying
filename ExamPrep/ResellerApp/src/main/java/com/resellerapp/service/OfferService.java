package com.resellerapp.service;

import com.resellerapp.model.dto.OfferAddDTO;
import com.resellerapp.model.dto.OfferDTO;
import com.resellerapp.model.dto.UserWithOffersDTO;
import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.entity.Offer;
import com.resellerapp.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ConditionService conditionService;
    private final AuthService authService;
    private final ModelMapper mapper;

    public OfferService(OfferRepository offerRepository,
                        ConditionService conditionService,
                        AuthService authService,
                        ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.conditionService = conditionService;
        this.authService = authService;
        this.mapper = mapper;
    }

    public void add(OfferAddDTO offerAddDTO) {
        Condition condition = this.conditionService.findByName(offerAddDTO.getCondition());

        Offer offer = this.mapper.map(offerAddDTO, Offer.class)
                .setCondition(condition)
                .setSeller(this.authService.getLoggedUser());

        this.offerRepository.saveAndFlush(offer);
    }

    @Transactional
    public List<OfferDTO> getOffersByLoggedUser() {
        return mapper.map(this.authService.getLoggedUser(), UserWithOffersDTO.class)
                .getOffers();
    }

    @Transactional
    public List<OfferDTO> getBoughtOffersByLoggedUser() {
        return mapper.map(this.authService.getLoggedUser(), UserWithOffersDTO.class)
                .getBoughtOffers();
    }

    public List<OfferDTO> getAllOtherOffers() {
        return this.offerRepository.findBySellerIdNot(this.authService.getLoggedUserId())
                .orElse(new ArrayList<>())
                .stream()
                .map(offer -> mapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }

    public void buyOffer(Long id) {
        Offer offer = this.offerRepository.findById(id).orElseThrow();

        offer.setSeller(null)
                .setBuyer(this.authService.getLoggedUser());

        this.offerRepository.saveAndFlush(offer);
    }

    @Transactional
    public void removeOffer(Long id) {
        this.offerRepository.deleteById(id);
    }
}
