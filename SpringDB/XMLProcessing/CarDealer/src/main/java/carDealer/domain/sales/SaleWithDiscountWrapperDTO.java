package carDealer.domain.sales;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleWithDiscountWrapperDTO {

    @XmlElement(name = "sale")
    private List<SaleWithDiscountDTO> sales;

    public SaleWithDiscountWrapperDTO() {
    }

    public SaleWithDiscountWrapperDTO(List<SaleWithDiscountDTO> sales) {
        this.sales = sales;
    }
}
