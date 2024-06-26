package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
import softuni.expirationManager.model.entities.ProductEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;
import softuni.expirationManager.utils.Constants;
import softuni.expirationManager.utils.DateTimeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DateTimeProvider dateTimeProvider;
    private final ModelMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          DateTimeProvider dateTimeProvider,
                          ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dateTimeProvider = dateTimeProvider;
        this.mapper = mapper;
    }

    public Long addProduct(ProductAddDTO productAddDTO, Long categoryId) {
        ProductEntity product = this.mapper.map(productAddDTO, ProductEntity.class);

        product.setCategory(this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException(Constants.NO_CATEGORY_FOUND)));

        ProductEntity savedProduct = this.productRepository.saveAndFlush(product);

        return savedProduct.getId();
    }

    public ProductViewDTO deleteById(Long productId) {
        ProductViewDTO productToDelete =
                this.mapper.map(this.productRepository.findById(productId).orElseThrow(
                        () -> new NoSuchElementException(Constants.NO_PRODUCT_FOUND)), ProductViewDTO.class);

        this.productRepository.deleteById(productId);

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
                .toList();
    }

    public List<ProductHomeViewDTO> getExpiredProducts(String username) {
        return this.productRepository.findAllByExpiryDateBeforeAndCategoryUserUsername(this.dateTimeProvider.getDateToday(), username)
                .orElse(new ArrayList<>())
                .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                .toList();
    }

    public List<ProductHomeViewDTO> getCloseToExpiryProducts(String username) {
        return this.productRepository.findAllByExpiryDateBeforeAndExpiryDateAfterAndCategoryUserUsername(
                        this.dateTimeProvider.getDateOneMonthForward(), this.dateTimeProvider.getDateToday(), username)
                .orElse(new ArrayList<>())
                .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                .toList();
    }

}
