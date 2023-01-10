package productShop.domain.dtos.product;

import productShop.domain.dtos.user.UserDTO;

import java.math.BigDecimal;

public class ProductDTO {

    private String name;
    private BigDecimal price;
    private UserDTO seller;

    public ProductDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    public ProductWithoutBuyerDTO toProductWithoutBuyerDTO() {
        return new ProductWithoutBuyerDTO(name, price, seller.getFullName());
    }

    public ProductBasicDTO toProductBasicDTO() {
        return new ProductBasicDTO(name, price);
    }

}
