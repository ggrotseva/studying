package carDealer.domain.cars.wrappers;

import carDealer.domain.cars.CarDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarWrapperDTO {

    @XmlElement(name = "car")
    private List<CarDTO> cars;

    public CarWrapperDTO() {
    }

    public CarWrapperDTO(List<CarDTO> cars) {
        this.cars = cars;
    }
}
