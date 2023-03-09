package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.model.dtos.CategoryAddDTO;
import softuni.expirationManager.service.CategoryService;

import java.io.IOException;
import java.security.Principal;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute
    public CategoryAddDTO initCategoryAddDTO() {
        return new CategoryAddDTO();
    }

    @GetMapping("/categories/add")
    public String getAddCategory() {
        return "category-add";
    }

    @PostMapping("/categories/add")
    public String postAddCategory(@Valid CategoryAddDTO categoryAddDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryAddDTO", categoryAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryAddDTO", bindingResult);

            return "redirect:/categories/add";
        }

        this.categoryService.addCategory(categoryAddDTO, principal.getName());

        return "redirect:/";
    }
}