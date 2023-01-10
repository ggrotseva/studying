package productShop.domain.dtos.category.wrappers;

import productShop.domain.dtos.category.CategoryImportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryImportWrapperDTO {

    @XmlElement(name = "category")
    List<CategoryImportDTO> categories;

    public CategoryImportWrapperDTO() {
    }

    public List<CategoryImportDTO> getCategories() {
        return categories;
    }
}
