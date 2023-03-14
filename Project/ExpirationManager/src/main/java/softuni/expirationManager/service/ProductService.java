package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.ProductAddDTO;
import softuni.expirationManager.model.dtos.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.ProductViewDTO;
import softuni.expirationManager.model.entities.ProductEntity;
import softuni.expirationManager.repository.CategoryRepository;
import softuni.expirationManager.repository.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public List<ProductViewDTO> findByCategory(Long id) {
        return this.productRepository.findByCategoryId(id).orElseThrow()
                .stream().map(p -> this.mapper.map(p, ProductViewDTO.class))
                .collect(Collectors.toList());
    }

    public void addProduct(ProductAddDTO productAddDTO, Long id) {
        ProductEntity product = this.mapper.map(productAddDTO, ProductEntity.class);

        product.setCategory(this.categoryRepository.findById(id).orElseThrow());

        this.productRepository.saveAndFlush(product);
    }

    public void deleteById(Long productId) {
        this.productRepository.deleteById(productId);
    }

    public List<ProductHomeViewDTO> getExpiredProducts(String username) {
        return this.productRepository.findByExpiryDateBeforeAndCategoryUserUsername(LocalDate.now(), username)
                .orElse(new ArrayList<>())
                        .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                        .collect(Collectors.toList());
    }

    public List<ProductHomeViewDTO> getCloseToExpiryProducts(String username) {
       return this.productRepository.findByExpiryDateBeforeAndCategoryUserUsername(LocalDate.now().plusMonths(1), username)
                .orElse(new ArrayList<>())
                        .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                        .collect(Collectors.toList());
    }
}
