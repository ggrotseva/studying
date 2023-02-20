package com.resellerapp.web;

import com.resellerapp.model.dto.OfferAddDTO;
import com.resellerapp.service.AuthService;
import com.resellerapp.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final AuthService authService;

    public OfferController(OfferService offerService, AuthService authService) {
        this.offerService = offerService;
        this.authService = authService;
    }

    @GetMapping("/add")
    public String getAddOffer() {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        return "offer-add";
    }

    @PostMapping("/add")
    public String postAddOffer(@Valid OfferAddDTO offerAddDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerAddDTO", offerAddDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.offerAddDTO", bindingResult);

            return "redirect:/offers/add";
        }

        this.offerService.add(offerAddDTO);

        return "redirect:/home";
    }

    @GetMapping("/buy/{id}")
    public String buyOffer(@PathVariable Long id) {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        this.offerService.buyOffer(id);

        return "redirect:/home";
    }

    @GetMapping("/remove/{id}")
    public String removeOffer(@PathVariable Long id) {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        this.offerService.removeOffer(id);

        return "redirect:/home";
    }


    //Model Attributes
    @ModelAttribute("offerAddDTO")
    public OfferAddDTO initOfferAddDTO() {
        return new OfferAddDTO();
    }
}
