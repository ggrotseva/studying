package productShop.domain.dtos.product.wrappers;

import productShop.domain.dtos.product.ProductImportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportWrapperDTO {

    @XmlElement(name = "product")
    List<ProductImportDTO> products;

    public ProductImportWrapperDTO() {
    }

    public List<ProductImportDTO> getProducts() {
        return products;
    }
}
