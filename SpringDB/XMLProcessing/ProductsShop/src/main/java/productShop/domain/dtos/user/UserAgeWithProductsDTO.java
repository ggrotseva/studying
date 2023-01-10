package productShop.domain.dtos.user;

import productShop.domain.dtos.product.ProductDTO;
import productShop.domain.dtos.product.ProductsWithCountWrapperDTO;
import productShop.domain.entities.Product;

import javax.xml.bind.annotation.*;
import java.util.Collection;
import java.util.Set;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserAgeWithProductsDTO implements Comparable<UserAgeWithProductsDTO> {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute
    private int age;

    @XmlElement(name = "sold-products")
    private ProductsWithCountWrapperDTO soldProducts;

    public UserAgeWithProductsDTO() {
    }

    public UserAgeWithProductsDTO(String firstName, String lastName, int age, Set<ProductDTO> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = new ProductsWithCountWrapperDTO(soldProducts);
    }

    public ProductsWithCountWrapperDTO getSoldProducts() {
        return soldProducts;
    }

    @Override
    public int compareTo(UserAgeWithProductsDTO other) {
        int result = other.getSoldProducts().getCount().compareTo(this.getSoldProducts().getCount());

        if (result == 0) {
            result = this.lastName.compareTo(other.lastName);
        }

        return result;
    }
}
