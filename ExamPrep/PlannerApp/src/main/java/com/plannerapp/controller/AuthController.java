package com.plannerapp.controller;

import com.plannerapp.model.dtos.UserLoginDTO;
import com.plannerapp.model.dtos.UserRegisterDTO;
import com.plannerapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/users/register")
    public String getRegister(Model model) {
        if (this.authService.isLogged()) {
            return "redirect:/home";
        }

        if (!model.containsAttribute("userRegisterDTO")) {
            model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        }

        return "register";
    }

    @PostMapping("/users/register")
    public String postRegister(@Valid UserRegisterDTO userRegisterDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (this.authService.isLogged()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors() || !this.authService.register(userRegisterDTO)) {
            redirectAttributes
                    .addFlashAttribute("userRegisterDTO", userRegisterDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);

            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }

    @GetMapping("/users/login")
    public String getLogin(Model model) {
        if (this.authService.isLogged()) {
            return "redirect:/home";
        }

        if (!model.containsAttribute("userLoginDTO")) {
            model.addAttribute("userLoginDTO", new UserLoginDTO());
        }

        return "login";
    }

    @PostMapping("/users/login")
    public String postLogin(@Valid UserLoginDTO userLoginDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (this.authService.isLogged()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userLoginDTO", userLoginDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);

            return "redirect:/users/login";
        }

        boolean isLogged = this.authService.login(userLoginDTO);

        if (!isLogged) {
            redirectAttributes
                    .addFlashAttribute("badCredentials", true)
                    .addFlashAttribute("userLoginDTO", userLoginDTO);

            return "redirect:/users/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/users/logout")
    public String logout() {
        this.authService.logout();
        return "redirect:/";
    }
}
