package softuni.exam.models.dto.wrappers;

import softuni.exam.models.dto.OfferImportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportWrapperDTO {

    @XmlElement(name = "offer")
    private List<OfferImportDTO> offers;

    public OfferImportWrapperDTO() {
    }

    public List<OfferImportDTO> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferImportDTO> offers) {
        this.offers = offers;
    }
}
