package carDealer.services.cars;

import carDealer.constants.OutputPaths;
import carDealer.constants.Utils;
import carDealer.domain.cars.CarDTO;
import carDealer.domain.cars.CarPartsDTO;
import carDealer.domain.cars.wrappers.CarPartsWrapperDTO;
import carDealer.domain.cars.wrappers.CarWrapperDTO;
import carDealer.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
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
    public List<CarDTO> findAllByMakeToyotaOrderByTravelledDistance() throws JAXBException {
        final List<CarDTO> carsByToyota = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream().map(car -> mapper.map(car, CarDTO.class))
                .collect(Collectors.toList());

        final CarWrapperDTO wrapperDTO = new CarWrapperDTO(carsByToyota);

        Utils.writeXmlIntoFile(wrapperDTO, OutputPaths.TOYOTA_CARS);

        return carsByToyota;
    }

    @Override
    public List<CarPartsDTO> getAllCarsWithParts() throws JAXBException {

        final List<CarPartsDTO> carsPartsDtos = this.carRepository.findAll().stream()
                .map(car -> mapper.map(car, CarPartsDTO.class))
                .collect(Collectors.toList());

        final CarPartsWrapperDTO wrapperDTO = new CarPartsWrapperDTO(carsPartsDtos);

        Utils.writeXmlIntoFile(wrapperDTO, OutputPaths.CARS_AND_PARTS);

        return carsPartsDtos;
    }
}
