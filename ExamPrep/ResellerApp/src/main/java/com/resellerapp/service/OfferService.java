package com.resellerapp.service;

import com.resellerapp.model.dto.OfferAddDTO;
import com.resellerapp.model.dto.OfferDTO;
import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.entity.User;
import com.resellerapp.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ConditionService conditionService;
    private final UserService userService;
    private final ModelMapper mapper;

    public OfferService(OfferRepository offerRepository,
                        ConditionService conditionService,
                        UserService userService,
                        ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.conditionService = conditionService;
        this.userService = userService;
        this.mapper = mapper;
    }

    public void add(OfferAddDTO offerAddDTO) {
        Condition condition = this.conditionService.findByName(offerAddDTO.getCondition());

        Offer offer = this.mapper.map(offerAddDTO, Offer.class);
        offer.setCondition(condition);

        this.offerRepository.saveAndFlush(offer);

        this.userService.addOfferByLoggedUser(offer);
    }

    @Transactional
    public List<OfferDTO> getOffersByUserId(Long id) {
        User user = this.userService.findById(id);
        return user.getOffers().stream()
                .map(offer -> mapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OfferDTO> getBoughtOffersByUserId(Long id) {
        User user = this.userService.findById(id);
        return user.getBoughtOffers().stream()
                .map(offer -> mapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OfferDTO> getAllOffers() {
        return this.offerRepository.findAll().stream()
                .map(offer -> mapper.map(offer, OfferDTO.class))
                .collect(Collectors.toList());
    }
}
