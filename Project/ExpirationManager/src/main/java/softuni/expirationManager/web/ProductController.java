package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.service.CategoryService;
import softuni.expirationManager.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("productAddDTO")
    public ProductAddDTO initProductAddDTO() {
        return new ProductAddDTO();
    }

    @GetMapping("/categories/{id}")
    public String getProductsByCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", this.categoryService.getCategoryNameIdDTO(id));

        List<ProductViewDTO> products = this.productService.findAllByCategoryId(id);

        model.addAttribute("products", products);

        return "category";
    }

    @PostMapping("/categories/{id}/addproduct")
    public String postAddProduct(@Valid ProductAddDTO productAddDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @PathVariable Long id) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddDTO", productAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddDTO", bindingResult);

            return "redirect:/categories/" + id;
        }

        this.productService.addProduct(productAddDTO, id);

        return "redirect:/categories/" + id;
    }

    @DeleteMapping("/categories/{id}/delproduct/{productId}")
    public String deleteProduct(@PathVariable("id") Long id, @PathVariable("productId") Long productId) {
        this.productService.deleteById(productId);

        return "redirect:/categories/" + id;
    }
}
