package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.service.ProductService;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories/{categoryId}")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("@categoryService.authorizeActions(#principal, #categoryId)")
    @GetMapping("/products")
    public ResponseEntity<List<ProductViewDTO>> getProductsOfCategory(@PathVariable("categoryId") Long categoryId,
                                                                      @AuthenticationPrincipal MyUserDetails principal) {

        return ResponseEntity.
                ok(this.productService.findAllByCategoryId(categoryId));
    }

    @PreAuthorize("@categoryService.authorizeActions(#principal, #categoryId)")
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductViewDTO> getProduct(@PathVariable("categoryId") Long categoryId,
                                                     @PathVariable("productId") Long productId,
                                                     @AuthenticationPrincipal MyUserDetails principal) {

        return ResponseEntity.
                ok(this.productService.findById(productId));
    }

    @PreAuthorize("@categoryService.authorizeActions(#principal, #categoryId)")
    @PostMapping(value = "/products", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductViewDTO> postProduct(@PathVariable("categoryId") Long categoryId,
                                                      @RequestBody @Valid ProductAddDTO productAddDTO,
                                                      @AuthenticationPrincipal MyUserDetails principal) {
        // TODO: better validation?

        Long productId = this.productService.addProduct(productAddDTO, categoryId);

        return ResponseEntity.created(URI.create(String.format("/categories/%d/products/%d", categoryId, productId)))
                .build();
    }

    @PreAuthorize("@categoryService.authorizeActions(#principal, #categoryId)")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductViewDTO> deleteComment(@PathVariable("categoryId") Long categoryId,
                                                        @PathVariable("productId") Long productId,
                                                        @AuthenticationPrincipal MyUserDetails principal) {

        return ResponseEntity.ok(this.productService.deleteById(productId));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // TODO: make it return custom Error class
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, String> fieldsErrors = new HashMap<>();
        fieldErrors.forEach(err -> fieldsErrors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(fieldsErrors);
    }

}
