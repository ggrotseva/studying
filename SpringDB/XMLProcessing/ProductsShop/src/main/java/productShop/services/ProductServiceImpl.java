package productShop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productShop.constants.OutputPaths;
import productShop.constants.Utils;
import productShop.domain.dtos.category.CategoryStats;
import productShop.domain.dtos.category.wrappers.CategoryStatsWrapperDTO;
import productShop.domain.dtos.product.ProductDTO;
import productShop.domain.dtos.product.ProductWithoutBuyerDTO;
import productShop.domain.dtos.product.wrappers.ProductWithoutBuyerWrapperDTO;
import productShop.repositories.ProductRepository;

import javax.xml.bind.JAXBException;
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
    public List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(float low, float high) throws JAXBException {
        final BigDecimal rangeStart = BigDecimal.valueOf(low);
        final BigDecimal rangeEnd = BigDecimal.valueOf(high);

        final List<ProductWithoutBuyerDTO> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(rangeStart, rangeEnd)
                .orElseThrow(NoSuchElementException::new)
                .stream().map(product -> mapper.map(product, ProductDTO.class))
                .map(ProductDTO::toProductWithoutBuyerDTO)
                .collect(Collectors.toList());

        final ProductWithoutBuyerWrapperDTO productsWrapper = new ProductWithoutBuyerWrapperDTO(products);

        Utils.writeXmlIntoFile(productsWrapper, OutputPaths.PRODUCTS_WITH_NO_BUYER_XML);

        return products;
    }

    @Override
    public List<CategoryStats> getCategoryStatistics() throws JAXBException {
        List<CategoryStats> stats = this.productRepository.getCategoryStats()
                .orElseThrow(NoSuchElementException::new);

        final CategoryStatsWrapperDTO categoriesWrapper = new CategoryStatsWrapperDTO(stats);

        Utils.writeXmlIntoFile(categoriesWrapper, OutputPaths.CATEGORIES_BY_PRODUCTS_XML);

        return stats;
    }
}
