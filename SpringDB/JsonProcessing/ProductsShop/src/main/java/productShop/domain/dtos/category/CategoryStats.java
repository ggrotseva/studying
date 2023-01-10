package productShop.domain.dtos.category;

import java.math.BigDecimal;

public class CategoryStats {
    private String category;
    private long productCount;
    private Double averagePrice;
    private BigDecimal totalRevenue;

    public CategoryStats(String category, long productCount, Double averagePrice, BigDecimal totalRevenue) {
        this.category = category;
        this.productCount = productCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
