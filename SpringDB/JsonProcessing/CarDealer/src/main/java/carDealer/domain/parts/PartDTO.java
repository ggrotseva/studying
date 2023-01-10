package carDealer.domain.parts;

import java.math.BigDecimal;

public class PartDTO {

    private String name;
    private BigDecimal price;

    public PartDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
