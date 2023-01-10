package productShop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productShop.constants.OutputPaths;
import productShop.constants.Utils;
import productShop.domain.dtos.category.CategoryStats;
import productShop.domain.dtos.product.ProductDTO;
import productShop.domain.dtos.product.ProductWithoutBuyerDTO;
import productShop.repositories.ProductRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(float low, float high) throws IOException {
        BigDecimal rangeStart = BigDecimal.valueOf(low);
        BigDecimal rangeEnd = BigDecimal.valueOf(high);

        List<ProductWithoutBuyerDTO> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(rangeStart, rangeEnd)
                .orElseThrow(NoSuchElementException::new)
                .stream().map(product -> mapper.map(product, ProductDTO.class))
                .map(ProductDTO::toProductWithoutBuyerDTO)
                .collect(Collectors.toList());

        Utils.writeJsonIntoFile(products, OutputPaths.PRODUCTS_WITH_NO_BUYER_JSON);

        return products;
    }

    @Override
    public List<CategoryStats> getCategoryStatistics() throws IOException {
        List<CategoryStats> stats = this.productRepository.getCategoryStats()
                .orElseThrow(NoSuchElementException::new);

        Utils.writeJsonIntoFile(stats, OutputPaths.CATEGORIES_BY_PRODUCTS_JSON);

        return stats;
    }
}
