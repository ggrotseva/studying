package carDealer.services.cars;

import carDealer.domain.cars.CarDTO;
import carDealer.domain.cars.CarPartsDTO;

import java.io.IOException;
import java.util.List;

public interface CarService {

    List<CarDTO> findAllByMakeToyotaOrderByTravelledDistance() throws IOException;

    List<CarPartsDTO> getAllCarsWithParts() throws IOException;
}
