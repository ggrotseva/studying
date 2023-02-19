package com.resellerapp.service;

import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.entity.User;
import com.resellerapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Transactional
    public void addOfferByLoggedUser(Offer offer) {
        User loggedUser = this.authService.getLoggedUser();
        loggedUser.addOffer(offer);

        this.userRepository.saveAndFlush(loggedUser);
    }

    @Transactional
    public void buyOfferByLoggedUser(Offer offer) {
        User loggedUser = this.authService.getLoggedUser();
        loggedUser.buyOffer(offer);

        this.userRepository.saveAndFlush(loggedUser);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }
}
