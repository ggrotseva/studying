package productShop.services;

import productShop.domain.dtos.category.CategoryStats;
import productShop.domain.dtos.product.ProductWithoutBuyerDTO;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(float low, float high) throws IOException;

    List<CategoryStats> getCategoryStatistics() throws IOException;
}
