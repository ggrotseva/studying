package carDealer.domain.parts.wrappers;

import carDealer.domain.parts.PartImportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartImportWrapperDTO {

    @XmlElement(name = "part")
    private List<PartImportDTO> parts;

    public PartImportWrapperDTO() {
    }

    public List<PartImportDTO> getParts() {
        return parts;
    }
}
