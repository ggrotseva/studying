package com.resellerapp.web;

import com.resellerapp.model.dto.OfferDTO;
import com.resellerapp.service.AuthService;
import com.resellerapp.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        List<OfferDTO> ownOffers = this.offerService.getOffersByLoggedUser();
        List<OfferDTO> boughtOffers = this.offerService.getBoughtOffersByLoggedUser();
        List<OfferDTO> otherOffers = this.offerService.getAllOtherOffers();

        model.addAttribute("ownOffers", ownOffers);
        model.addAttribute("boughtOffers", boughtOffers);
        model.addAttribute("otherOffers", otherOffers);

        return "home";
    }

}
