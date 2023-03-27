package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.category.CategoryAddDTO;
import softuni.expirationManager.model.dtos.category.CategoryEditDTO;
import softuni.expirationManager.model.dtos.category.CategoryViewDTO;
import softuni.expirationManager.service.CategoryService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String getCategories(Principal principal, Model model) {
        List<CategoryViewDTO> categories = this.categoryService.findAllByUserUsername(principal.getName());

        model.addAttribute("categories", categories);

        return "categories";
    }

    @GetMapping("/categories/add")
    public String getAddCategory(Model model) {

        if (!model.containsAttribute("categoryAddDTO")) {
            model.addAttribute("categoryAddDTO", new CategoryAddDTO());
        }

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

        return "redirect:/categories";
    }

    @PreAuthorize("@categoryService.isOwnerOrAdmin(#principal, #id)")
    @GetMapping("/categories/{id}/edit")
    public String getEditCategory(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails principal) {

        if (!model.containsAttribute("categoryEditDTO")) {
            model.addAttribute("categoryEditDTO", this.categoryService.getCategoryEditDtoById(id));
        }

        return "category-edit";
    }

    @PreAuthorize("@categoryService.isOwnerOrAdmin(#principal, #categoryEditDTO.getId())")
    @PutMapping("/categories/{id}/edit")
    public String putEditCategory(@Valid CategoryEditDTO categoryEditDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal MyUserDetails principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryEditDTO", categoryEditDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryEditDTO", bindingResult);

            return "redirect:/categories/" + categoryEditDTO.getId() + "/edit";
        }

        this.categoryService.editCategory(categoryEditDTO);

        return "redirect:/categories";
    }

    @PreAuthorize("@categoryService.isOwnerOrAdmin(#principal, #id)")
    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails principal) {

        this.categoryService.deleteById(id);

        return "redirect:/categories";
    }

    @PreAuthorize("@categoryService.isOwnerOrAdmin(#principal, #id)")
    @GetMapping("/categories/{id}")
    public String getProductsByCategory(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails principal) {

        model.addAttribute("category", this.categoryService.getCategoryNameIdDTO(id));

        return "category";
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ModelAndView handleFileSizeLimitExceeded() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("errorMessage", "File you uploaded exceeds maximum allowed size");

        return mav;
    }

}
