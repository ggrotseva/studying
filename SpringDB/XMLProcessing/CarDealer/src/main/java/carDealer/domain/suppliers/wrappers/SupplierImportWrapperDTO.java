package carDealer.domain.suppliers.wrappers;

import carDealer.domain.suppliers.SupplierImportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierImportWrapperDTO {

    @XmlElement(name = "supplier")
    private List<SupplierImportDTO> suppliers;

    public SupplierImportWrapperDTO() {
    }

    public List<SupplierImportDTO> getSuppliers() {
        return suppliers;
    }

}
