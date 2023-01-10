package productShop.domain.dtos.product;

import java.math.BigDecimal;

public class ProductBasicDTO {

    private String name;
    private BigDecimal price;

    public ProductBasicDTO() {
    }

    public ProductBasicDTO(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
