package softuni.expirationManager.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.expirationManager.model.dtos.ProductViewDTO;
import softuni.expirationManager.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductViewDTO>> getProductsOfCategory(@PathVariable("id") Long categoryId) {

        return ResponseEntity.
                ok(this.productService.findByCategory(categoryId));
    }


}
