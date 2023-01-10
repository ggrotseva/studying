package softuni.exam.models.dto;

import softuni.exam.util.LocalDateXmlAdapter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDTO {

    @XmlElement
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    @NotNull
    private LocalDate publishedOn;

    @XmlElement
    @NotNull
    private AgentNameDTO agent;

    @XmlElement
    @NotNull
    private ApartmentIdDTO apartment;

    public OfferImportDTO() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public AgentNameDTO getAgent() {
        return agent;
    }

    public void setAgent(AgentNameDTO agent) {
        this.agent = agent;
    }

    public ApartmentIdDTO getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentIdDTO apartment) {
        this.apartment = apartment;
    }
}
