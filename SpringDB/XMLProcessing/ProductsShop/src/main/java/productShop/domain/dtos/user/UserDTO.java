package productShop.domain.dtos.user;

import productShop.domain.dtos.product.ProductDTO;
import productShop.domain.dtos.product.ProductsWithCountWrapperDTO;

import java.util.Set;

public class UserDTO {

    private String firstName;
    private String lastName;
    private Integer age;
    private Set<ProductDTO> sellingProducts;

    public UserDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<ProductDTO> getSellingProducts() {
        return sellingProducts;
    }

    public void setSellingProducts(Set<ProductDTO> sellingProducts) {
        this.sellingProducts = sellingProducts;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public UserAgeWithProductsDTO toUserAgeWithProductsDTO() {
        return new UserAgeWithProductsDTO(firstName, lastName, age, sellingProducts);
    }

}
