package com.likebookapp.controller;

import com.likebookapp.model.dtos.PostAddDTO;
import com.likebookapp.service.AuthService;
import com.likebookapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PostController {

    private final AuthService authService;
    private final PostService postService;

    @Autowired
    public PostController(AuthService authService, PostService postService) {
        this.authService = authService;
        this.postService = postService;
    }

    @GetMapping("/posts/add")
    public String getAddPost(Model model) {

        if (!this.authService.isLogged()) {
            return "redirect:/";
        }

        if (!model.containsAttribute("postAddDTO")) {
            model.addAttribute("postAddDTO", new PostAddDTO());
        }

        return "post-add";
    }

    @PostMapping("/posts/add")
    public String postAddPost(@Valid PostAddDTO postAddDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (!this.authService.isLogged()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("postAddDTO", postAddDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.postAddDTO", bindingResult);

            return "redirect:/posts/add";
        }

        this.postService.addPost(postAddDTO);

        return "redirect:/home";
    }

    @GetMapping("/posts/like/{id}")
    public String likePost(@PathVariable Long id) {

        if (!this.authService.isLogged()) {
            return "redirect:/";
        }

        this.postService.likePost(id);

        return "redirect:/home";
    }

    @GetMapping("/posts/remove/{id}")
    public String deletePost(@PathVariable Long id) {

        if (!this.authService.isLogged()) {
            return "redirect:/";
        }

        this.postService.deletePost(id);

        return "redirect:/home";
    }
}
