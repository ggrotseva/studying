package carDealer.domain.sales;

import carDealer.domain.cars.Car;
import carDealer.domain.cars.CarNoIdXmlAttributesDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleWithDiscountDTO {

    @XmlElement
    private CarNoIdXmlAttributesDTO car;

    @XmlElement(name = "customer-name")
    private String customerName;

    @XmlElement
    private double discount;

    @XmlElement
    private BigDecimal price;

    @XmlElement(name = "price-with-discount")
    private BigDecimal priceWithDiscount;

    public SaleWithDiscountDTO() {
    }

    public SaleWithDiscountDTO(Car car, String customerName,
                               double discount, BigDecimal price, BigDecimal priceWithDiscount) {
        this.car = car.toCarXmlAttributesDTO();
        this.customerName = customerName;
        this.discount = discount;
        this.price = price;
        this.priceWithDiscount = priceWithDiscount;
    }

    public void setCar(CarNoIdXmlAttributesDTO car) {
        this.car = car;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
