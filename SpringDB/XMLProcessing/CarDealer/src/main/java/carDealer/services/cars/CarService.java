package carDealer.services.cars;

import carDealer.domain.cars.CarDTO;
import carDealer.domain.cars.CarPartsDTO;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface CarService {

    List<CarDTO> findAllByMakeToyotaOrderByTravelledDistance() throws JAXBException;

    List<CarPartsDTO> getAllCarsWithParts() throws JAXBException;
}
