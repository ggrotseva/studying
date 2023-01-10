package carDealer.domain.cars;

import carDealer.domain.parts.PartDTO;

import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarPartsDTO {

    @XmlAttribute
    private String make;

    @XmlAttribute
    private String model;

    @XmlAttribute(name = "travelled-distance")
    private long travelledDistance;

    @XmlElementWrapper(name = "parts")
    @XmlElement(name = "part")
    private Set<PartDTO> parts;

    public CarPartsDTO() {
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public void setParts(Set<PartDTO> parts) {
        this.parts = parts;
    }
}
