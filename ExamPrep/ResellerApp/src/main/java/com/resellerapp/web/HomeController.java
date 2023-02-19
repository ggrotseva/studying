package com.resellerapp.web;

import com.resellerapp.model.dto.OfferDTO;
import com.resellerapp.service.AuthService;
import com.resellerapp.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final OfferService offerService;

    public HomeController(AuthService authService, OfferService offerService) {
        this.authService = authService;
        this.offerService = offerService;
    }

    @GetMapping("/")
    public String loggedOutIndex() {
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String loggedInIndex(Model model) {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        Long loggedUserId = this.authService.getLoggedUserId();

        List<OfferDTO> ownOffers = this.offerService.getOffersByUserId(loggedUserId);
        List<OfferDTO> boughtOffers = this.offerService.getBoughtOffersByUserId(loggedUserId);
        List<OfferDTO> allOffers = this.offerService.getAllOffers();

        model.addAttribute("ownOffers", ownOffers);
        model.addAttribute("boughtOffers", boughtOffers);
        model.addAttribute("allOffers", allOffers);

        return "home";
    }

    @PostMapping("/offer/buy")
    public String postBuyOffer() {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }


        return "home";
    }
}
