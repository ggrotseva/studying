package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.dtos.product.ProductAddDTO;
import softuni.expirationManager.model.dtos.product.ProductHomeViewDTO;
import softuni.expirationManager.model.dtos.product.ProductViewDTO;
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

    public boolean isOwnerOrAdmin(MyUserDetails userDetails, Long categoryId) {
        Long categoryUserId = this.categoryRepository.findById(categoryId).orElseThrow()
                .getUser().getId();

        return userDetails.getId().equals(categoryUserId)
                || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public List<ProductViewDTO> findAllByCategoryId(Long id) {
        return this.productRepository.findAllByCategoryId(id).orElseThrow()
                .stream().map(p -> this.mapper.map(p, ProductViewDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductHomeViewDTO> getExpiredProducts(String username) {
        return this.productRepository.findAllByExpiryDateBeforeAndCategoryUserUsername(LocalDate.now(), username)
                .orElse(new ArrayList<>())
                .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductHomeViewDTO> getCloseToExpiryProducts(String username) {
        return this.productRepository.findAllByExpiryDateBeforeAndExpiryDateAfterAndCategoryUserUsername(
                        LocalDate.now().plusMonths(1), LocalDate.now(), username)
                .orElse(new ArrayList<>())
                .stream().map(p -> this.mapper.map(p, ProductHomeViewDTO.class))
                .collect(Collectors.toList());
    }

    public ProductViewDTO addProduct(ProductAddDTO productAddDTO, Long categoryId) {
        ProductEntity product = this.mapper.map(productAddDTO, ProductEntity.class);

        product.setCategory(this.categoryRepository.findById(categoryId).orElseThrow());

        return this.mapper.map(this.productRepository.saveAndFlush(product), ProductViewDTO.class);
    }

    public void deleteById(Long productId) {
        this.productRepository.deleteById(productId);
    }

    public ProductViewDTO findById(Long productId) {
        return this.mapper.map(this.productRepository.findById(productId).orElseThrow(), ProductViewDTO.class);
    }

}
