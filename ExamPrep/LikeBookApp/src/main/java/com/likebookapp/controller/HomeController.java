package com.likebookapp.controller;

import com.likebookapp.model.dtos.PostViewDTO;
import com.likebookapp.service.AuthService;
import com.likebookapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final PostService postService;

    @Autowired
    public HomeController(AuthService authService, PostService postService) {
        this.authService = authService;
        this.postService = postService;
    }

    @GetMapping("/")
    public String getIndex() {

        if (this.authService.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model) {

        if (!this.authService.isLogged()) {
            return "redirect:/";
        }

        List<PostViewDTO> ownPosts = this.postService.getPostsByCurrentUser();
        List<PostViewDTO> othersPosts = this.postService.getPostsByCurrentUserNot();

        model.addAttribute("ownPosts", ownPosts);
        model.addAttribute("othersPosts", othersPosts);

        return "home";
    }
}
