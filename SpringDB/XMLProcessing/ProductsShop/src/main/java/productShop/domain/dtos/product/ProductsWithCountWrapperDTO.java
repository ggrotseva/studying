package productShop.domain.dtos.product;

import javax.xml.bind.annotation.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsWithCountWrapperDTO {

    @XmlAttribute
    private Integer count;

    @XmlElement(name = "product")
    private Set<ProductBasicDTO> products;

    public ProductsWithCountWrapperDTO() {
    }

    public ProductsWithCountWrapperDTO(Set<ProductDTO> soldProducts) {
        this.products = soldProducts.stream()
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

}
