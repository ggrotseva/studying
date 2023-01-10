package productShop.domain.dtos.product;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductsWithCountWrapperDTO {

    private Integer count;
    private Set<ProductBasicDTO> products;

    public ProductsWithCountWrapperDTO() {
    }

    public ProductsWithCountWrapperDTO(Set<ProductDTO> productDtos) {
        this.products = productDtos.stream()
                .map(ProductDTO::toProductBasicDTO)
                .collect(Collectors.toSet());
        this.count = products.size();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<ProductBasicDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductBasicDTO> products) {
        this.products = products;
    }
}
