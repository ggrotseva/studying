package carDealer.domain.cars;

import carDealer.domain.parts.PartDTO;

import java.util.Set;

public class CarPartsDTO {

    private CarNoIdDTO car;
    private Set<PartDTO> parts;

    public CarPartsDTO() {
    }

    public void setCar(CarNoIdDTO car) {
        this.car = car;
    }

    public void setParts(Set<PartDTO> parts) {
        this.parts = parts;
    }
}
