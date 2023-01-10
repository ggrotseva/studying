package productShop.domain.dtos.product.wrappers;

import productShop.domain.dtos.product.ProductWithoutBuyerDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductWithoutBuyerWrapperDTO {

    @XmlElement(name = "product")
    private List<ProductWithoutBuyerDTO> products;

    public ProductWithoutBuyerWrapperDTO() {
    }

    public ProductWithoutBuyerWrapperDTO( List<ProductWithoutBuyerDTO> products) {
        this.products = products;
    }

    public void setProducts(List<ProductWithoutBuyerDTO> products) {
        this.products = products;
    }

}
