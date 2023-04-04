package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.model.entities.ProductEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public Long addProduct(ProductAddDTO productAddDTO, Long categoryId) {
        ProductEntity product = this.mapper.map(productAddDTO, ProductEntity.class);

        product.setCategory(this.categoryRepository.findById(categoryId).orElseThrow());

        ProductEntity savedProduct = this.productRepository.saveAndFlush(product);

        if (savedProduct.getExpiryDate().isBefore(LocalDate.now())) {
            updateExpiredProducts();
        } else if (savedProduct.getExpiryDate().isBefore(LocalDate.now().plusMonths(1))) {
            updateCloseToExpiryProducts();
        }

        return savedProduct.getId();
    }

    public ProductViewDTO deleteById(Long productId) {
        ProductViewDTO productToDelete =
                this.mapper.map(this.productRepository.findById(productId).orElseThrow(
                        () -> new NoSuchElementException(Constants.NO_PRODUCT_FOUND)), ProductViewDTO.class);

        this.productRepository.deleteById(productId);

        if (productToDelete.getExpiryDate().isBefore(LocalDate.now().plusMonths(1))) {
            updateExpiredProducts();
        } else if (productToDelete.getExpiryDate().isBefore(LocalDate.now().plusMonths(1))) {
            updateCloseToExpiryProducts();
        }

        return productToDelete;
    }

    public ProductViewDTO findById(Long productId) {
        return this.mapper.map(this.productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(Constants.NO_PRODUCT_FOUND)), ProductViewDTO.class);
    }

    public List<ProductViewDTO> findAllByCategoryId(Long id) {
        return this.productRepository.findAllByCategoryId(id)
                    .orElseThrow(() -> new NoSuchElementException(Constants.NO_PRODUCT_FOUND))
                .stream().map(p -> this.mapper.map(p, ProductViewDTO.class))
                .collect(Collectors.toList());
    }

    @Cacheable("expiredProducts")
    public List<ProductHomeViewDTO> getExpiredProducts(String username) {
        return this.productRepository.findAllByExpiryDateBeforeAndCategoryUserUsername(LocalDate.now(), username)
                .orElse(new ArrayList<>())
                .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                .collect(Collectors.toList());
    }

    @Cacheable("closeToExpiryProducts")
    public List<ProductHomeViewDTO> getCloseToExpiryProducts(String username) {
        return this.productRepository.findAllByExpiryDateBeforeAndExpiryDateAfterAndCategoryUserUsername(
                        LocalDate.now().plusMonths(1), LocalDate.now(), username)
                .orElse(new ArrayList<>())
                .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                .collect(Collectors.toList());
    }

    @CachePut("expiredProducts")
    public List<ProductHomeViewDTO> updateExpiredProducts() {
        return this.productRepository.findAllByExpiryDateBeforeAndCategoryUserUsername(LocalDate.now(), SecurityContextHolder.getContext().getAuthentication().getName())
                .orElse(new ArrayList<>())
                .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                .collect(Collectors.toList());
    }

    @CachePut("closeToExpiryProducts")
    public List<ProductHomeViewDTO> updateCloseToExpiryProducts() {
        return this.productRepository.findAllByExpiryDateBeforeAndExpiryDateAfterAndCategoryUserUsername(
                        LocalDate.now().plusMonths(1), LocalDate.now(), SecurityContextHolder.getContext().getAuthentication().getName())
                .orElse(new ArrayList<>())
                .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                .collect(Collectors.toList());
    }

}
