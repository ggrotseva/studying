package carDealer.domain.sales;

import carDealer.domain.cars.Car;
import carDealer.domain.cars.CarNoIdDTO;

import java.math.BigDecimal;

public class SaleWithDiscountDTO {

    private CarNoIdDTO car;
    private String customerName;
    private double discount;
    private BigDecimal price;
    private BigDecimal priceWithDiscount;

    public SaleWithDiscountDTO(Car car, String customerName,
                               double discount, BigDecimal price, BigDecimal priceWithDiscount) {
        this.car = car.toCarNoIdDTO();
        this.customerName = customerName;
        this.discount = discount;
        this.price = price;
        this.priceWithDiscount = priceWithDiscount;
    }

    public void setCar(CarNoIdDTO car) {
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
