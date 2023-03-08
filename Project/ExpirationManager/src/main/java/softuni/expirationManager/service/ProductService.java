package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.ProductViewDTO;
import softuni.expirationManager.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public ProductService(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<ProductViewDTO> findByCategory(Long id) {
        return this.productRepository.findByCategoryId(id).orElseThrow()
                .stream().map(p -> this.mapper.map(p, ProductViewDTO.class))
                .collect(Collectors.toList());
    }
}
