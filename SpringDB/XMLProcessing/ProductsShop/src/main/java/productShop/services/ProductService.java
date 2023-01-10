package productShop.services;

import productShop.domain.dtos.category.CategoryStats;
import productShop.domain.dtos.product.ProductWithoutBuyerDTO;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface ProductService {

    List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(float low, float high) throws JAXBException;

    List<CategoryStats> getCategoryStatistics() throws JAXBException;
}
