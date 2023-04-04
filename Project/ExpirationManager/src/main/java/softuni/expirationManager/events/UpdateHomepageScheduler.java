package softuni.expirationManager.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;
import softuni.expirationManager.service.ProductService;
import softuni.expirationManager.service.RecipeService;

import java.security.Principal;
import java.util.List;

@Component
public class UpdateHomepageScheduler {

    private final ProductService productService;

    @Autowired
    public UpdateHomepageScheduler(ProductService productService) {
        this.productService = productService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateHomepage() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        this.productService.updateExpiredProducts();
        this.productService.updateCloseToExpiryProducts();
    }
}
