package softuni.exam.models.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PartSeedDTO {

    @Size(min = 2, max = 19)
    private String partName;

    @DecimalMin("10")
    @DecimalMax("2000")
    private BigDecimal price;

    @DecimalMin(value = "0", inclusive = false)
    private int quantity;

    public PartSeedDTO() {
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
