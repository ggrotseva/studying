package productShop.domain.dtos.user;

import productShop.domain.dtos.product.SoldProductDTO;

import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductDTO {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    Set<SoldProductDTO> sellingProducts;

    public UserWithSoldProductDTO() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSellingProducts(Set<SoldProductDTO> sellingProducts) {
        this.sellingProducts = sellingProducts;
    }

}
