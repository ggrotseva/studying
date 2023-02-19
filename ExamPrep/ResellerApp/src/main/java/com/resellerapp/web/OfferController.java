package com.resellerapp.web;

import com.resellerapp.model.dto.OfferAddDTO;
import com.resellerapp.service.AuthService;
import com.resellerapp.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class OfferController {

    private final OfferService offerService;
    private final AuthService authService;

    public OfferController(OfferService offerService, AuthService authService) {
        this.offerService = offerService;
        this.authService = authService;
    }

    @GetMapping("/offers/add")
    public String getAddOffer() {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }

        return "offer-add";
    }

    @PostMapping("/offers/add")
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

    //Model Attributes
    @ModelAttribute("offerAddDTO")
    public OfferAddDTO initOfferAddDTO() {
        return new OfferAddDTO();
    }
}
