package productShop.domain.dtos.product;

import java.math.BigDecimal;

public class ProductWithoutBuyerDTO {

    private String name;
    private BigDecimal price;
    private String seller;

    public ProductWithoutBuyerDTO(String name, BigDecimal price, String sellerNames) {
        this.name = name;
        this.price = price;
        this.seller = sellerNames;
    }

    // only setters needed, because we're mapping TO dto, not FROM
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}

