package carDealer.services.cars;

import carDealer.constants.OutputPaths;
import carDealer.constants.Utils;
import carDealer.domain.cars.Car;
import carDealer.domain.cars.CarDTO;
import carDealer.domain.cars.CarPartsDTO;
import carDealer.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper mapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CarDTO> findAllByMakeToyotaOrderByTravelledDistance() throws IOException {
        List<CarDTO> carsByToyota = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream().map(car -> mapper.map(car, CarDTO.class))
                .collect(Collectors.toList());

        Utils.writeJsonIntoFile(carsByToyota, OutputPaths.TOYOTA_CARS);

        return carsByToyota;
    }

    @Override
    public List<CarPartsDTO> getAllCarsWithParts() throws IOException {

        List<Car> allCars = this.carRepository.findAll();

        List<CarPartsDTO> carsPartsDtos = allCars.stream().map(car -> mapper.map(car, CarPartsDTO.class))
                .collect(Collectors.toList());

        Utils.writeJsonIntoFile(carsPartsDtos, OutputPaths.CARS_AND_PARTS);

        return carsPartsDtos;
    }
}
