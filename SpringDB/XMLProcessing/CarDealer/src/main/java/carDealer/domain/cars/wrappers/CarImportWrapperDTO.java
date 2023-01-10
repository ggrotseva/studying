package carDealer.domain.cars.wrappers;

import carDealer.domain.cars.CarNoIdDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportWrapperDTO {

    @XmlElement(name = "car")
    private List<CarNoIdDTO> cars;

    public CarImportWrapperDTO() {
    }

    public List<CarNoIdDTO> getCars() {
        return cars;
    }
}
