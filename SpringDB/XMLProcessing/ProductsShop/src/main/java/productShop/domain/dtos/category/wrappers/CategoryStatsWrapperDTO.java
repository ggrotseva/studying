package productShop.domain.dtos.category.wrappers;

import productShop.domain.dtos.category.CategoryStats;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryStatsWrapperDTO {

    @XmlElement(name = "category")
    private List<CategoryStats> categories;

    public CategoryStatsWrapperDTO() {
    }

    public CategoryStatsWrapperDTO(List<CategoryStats> categories) {
        this.categories = categories;
    }
}
