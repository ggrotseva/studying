package softuni.expirationManager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.service.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories/{categoryId}")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("@categoryService.authorizeActions(#userDetails, #categoryId)")
    @GetMapping("/products")
    public ResponseEntity<List<ProductViewDTO>> getProductsOfCategory(@PathVariable("categoryId") Long categoryId,
                                                                      @AuthenticationPrincipal MyUserDetails userDetails) {

        return ResponseEntity.
                ok(this.productService.findAllByCategoryId(categoryId));
    }

    @PreAuthorize("@categoryService.authorizeActions(#userDetails, #categoryId)")
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductViewDTO> getProduct(@PathVariable("categoryId") Long categoryId,
                                                     @PathVariable("productId") Long productId,
                                                     @AuthenticationPrincipal MyUserDetails userDetails) {

        return ResponseEntity.
                ok(this.productService.findById(productId));
    }

    @PreAuthorize("@categoryService.authorizeActions(#userDetails, #categoryId)")
    @PostMapping(value = "/products", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductViewDTO> postProduct(@PathVariable("categoryId") Long categoryId,
                                                      @RequestBody ProductAddDTO productAddDTO,
                                                      @AuthenticationPrincipal MyUserDetails userDetails) {

        Long productId = this.productService.addProduct(productAddDTO, categoryId);

        return ResponseEntity.created(URI.create(String.format("/categories/%d/products/%d", categoryId, productId)))
                .build();
    }

    @PreAuthorize("@categoryService.authorizeActions(#userDetails, #categoryId)")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductViewDTO> deleteProduct(@PathVariable("categoryId") Long categoryId,
                                                        @PathVariable("productId") Long productId,
                                                        @AuthenticationPrincipal MyUserDetails userDetails) {

        return ResponseEntity.ok(this.productService.deleteById(productId));
    }

}
