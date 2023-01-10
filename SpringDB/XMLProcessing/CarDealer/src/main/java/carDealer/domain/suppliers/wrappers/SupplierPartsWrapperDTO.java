package carDealer.domain.suppliers.wrappers;

import carDealer.domain.suppliers.SupplierPartsDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierPartsWrapperDTO {

    @XmlElement(name = "supplier")
    private List<SupplierPartsDTO> suppliers;

    public SupplierPartsWrapperDTO() {
    }

    public SupplierPartsWrapperDTO(List<SupplierPartsDTO> suppliers) {
        this.suppliers = suppliers;
    }
}
