package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.CityImportDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper mapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository,
                           ValidationUtils validationUtils,
                           Gson gson, ModelMapper mapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Paths.CITIES_JSON_PATH);
    }

    @Override
    public String importCities() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        if (!areImported()) {
            final CityImportDTO[] cities = this.gson.fromJson(readCitiesFileContent(), CityImportDTO[].class);

            for (CityImportDTO city : cities) {
                boolean isValid = this.validationUtils.isValid(city) &&
                        this.cityRepository.findFirstByCityName(city.getCityName()).isEmpty();

                if (isValid) {
                    stringBuilder.append(String.format(Messages.VALID_CITY_FORMAT, city.getCityName(), city.getPopulation()))
                            .append(System.lineSeparator());

                    City cityToSave = mapper.map(city, City.class);

                    cityToSave.setCountry(this.countryRepository.getById(city.getCountry()));

                    this.cityRepository.saveAndFlush(cityToSave);
                } else {
                    stringBuilder.append(Messages.INVALID_CITY).append(System.lineSeparator());
                }
            }
        }

        return stringBuilder.toString();
    }
}
