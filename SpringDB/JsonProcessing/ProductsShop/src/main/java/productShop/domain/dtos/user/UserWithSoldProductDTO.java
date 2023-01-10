package productShop.domain.dtos.user;

import com.google.gson.annotations.SerializedName;
import productShop.domain.dtos.product.SoldProductDTO;

import java.util.Set;

public class UserWithSoldProductDTO {

    private String firstName;
    private String lastName;
    @SerializedName("soldProducts")
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
