package softuni.expirationManager.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.service.AuthService;
import softuni.expirationManager.service.ProductService;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/categories/{id}")
public class ProductRestController {

    private final ProductService productService;
    private final AuthService authService;

    public ProductRestController(ProductService productService, AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductViewDTO>> getProductsOfCategory(@PathVariable("id") Long categoryId) {

        return ResponseEntity.
                ok(this.productService.findAllByCategoryId(categoryId));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductViewDTO> getProduct(@PathVariable("productId") Long productId) {

        return ResponseEntity.
                ok(this.productService.findById(productId));
    }

    @PostMapping(value = "/products", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductViewDTO> postProduct(@PathVariable("id") Long categoryId,
                                                      @RequestBody ProductAddDTO productAddDTO) {

        ProductViewDTO product = this.productService.addProduct(productAddDTO, categoryId);

        return ResponseEntity.created(URI.create(String.format("/categories/%d/products/%d", categoryId, product.getId())))
                .body(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductViewDTO> deleteComment(@PathVariable("productId") Long productId,
                                                        Principal principal) {

        ProductViewDTO productForDelete = this.productService.findById(productId);

        if (authorizeDelete(principal.getName(), productForDelete)) {

            this.productService.deleteById(productId);
            return ResponseEntity.ok(productForDelete);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean authorizeDelete(String username, ProductViewDTO productForDelete) {
        return this.authService.authorizePrincipal(username) ||
                productForDelete.getCategoryUserUsername().equals(username);
    }

//    private boolean authorizeCreate(String username, ProductViewDTO productForDelete) {
//        return this.authService.authorizePrincipal(username) ||
//                productForDelete.getCategoryUserUsername().equals(username);
//    }

}
