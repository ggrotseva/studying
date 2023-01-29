package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.wrappers.CarSeedWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CarServiceImpl implements CarService {

    private final String CARS_XML_PATH = "src/main/resources/files/xml/cars.xml";

    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, XmlParser xmlParser, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_XML_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        final StringBuilder output = new StringBuilder();

        final CarSeedWrapperDTO wrapper = this.xmlParser.fromXml(CARS_XML_PATH, CarSeedWrapperDTO.class);

        wrapper.getCars().stream()
                .filter(carDTO -> {
                    boolean isValid = this.validationUtil.isValid(carDTO)
                            && this.carRepository.findByPlateNumber(carDTO.getPlateNumber()).isEmpty();

                    output.append(isValid ?
                            String.format("Successfully imported car %s - %s",
                                    carDTO.getCarMake(), carDTO.getCarModel())
                            : "Invalid car")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(carDTO -> this.mapper.map(carDTO, Car.class))
                .forEach(carRepository::save);

        return output.toString();
    }
}
