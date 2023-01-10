package productShop.domain.dtos.user;

import productShop.domain.dtos.product.ProductDTO;
import productShop.domain.dtos.product.ProductsWithCountWrapperDTO;

import java.util.Set;

public class UserAgeWithProductsDTO {

    private String firstName;
    private String lastName;
    private int age;
    private ProductsWithCountWrapperDTO soldProducts;

    public UserAgeWithProductsDTO() {
    }

    public UserAgeWithProductsDTO(String firstName, String lastName, int age, Set<ProductDTO> sellingProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = new ProductsWithCountWrapperDTO(sellingProducts);
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ProductsWithCountWrapperDTO getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductsWithCountWrapperDTO soldProducts) {
        this.soldProducts = soldProducts;
    }
}
