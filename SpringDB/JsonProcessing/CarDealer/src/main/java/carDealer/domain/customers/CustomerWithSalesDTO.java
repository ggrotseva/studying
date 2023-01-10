package carDealer.domain.customers;

import java.math.BigDecimal;

public class CustomerWithSalesDTO {

    private String fullName;
    private long boughtCars;
    private BigDecimal spentMoney;

    public CustomerWithSalesDTO(String fullName, long boughtCars, BigDecimal spentMoney) {
        this.fullName = fullName;
        this.boughtCars = boughtCars;
        this.spentMoney = spentMoney;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBoughtCars(long boughtCars) {
        this.boughtCars = boughtCars;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }
}
