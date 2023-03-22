package softuni.expirationManager.web;

import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.service.ProductService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories/{id}")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
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
                                                      @RequestBody @Valid ProductAddDTO productAddDTO) {

        ProductViewDTO product = this.productService.addProduct(productAddDTO, categoryId);

        return ResponseEntity.created(URI.create(String.format("/categories/%d/products/%d", categoryId, product.getId())))
                .body(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductViewDTO> deleteComment(@PathVariable("productId") Long productId,
                                                        @AuthenticationPrincipal MyUserDetails userDetails) {

        ProductViewDTO productForDelete = this.productService.findById(productId);

        if (authorizeDelete(userDetails, productForDelete)) {

            this.productService.deleteById(productId);
            return ResponseEntity.ok(productForDelete);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        List<String> errorMessages = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

        Map<String, String> fieldsErrors = new HashMap<>();

        fieldErrors.forEach(err -> fieldsErrors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(fieldsErrors);
    }

    private boolean authorizeDelete(MyUserDetails userDetails, ProductViewDTO productForDelete) {
        return userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ||
                productForDelete.getCategoryUserId().equals(userDetails.getId());
    }

}
