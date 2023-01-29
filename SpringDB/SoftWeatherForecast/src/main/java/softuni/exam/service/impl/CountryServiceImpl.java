package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.CountryImportDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static softuni.exam.constants.Messages.*;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper mapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ValidationUtils validationUtils,
                              Gson gson, ModelMapper mapper) {
        this.countryRepository = countryRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Paths.COUNTRIES_JSON_PATH);
    }

    @Override
    public String importCountries() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        if (!areImported()) {
            final CountryImportDTO[] countryImportDTOS = gson.fromJson(readCountriesFromFile(), CountryImportDTO[].class);

            for (CountryImportDTO country : countryImportDTOS) {
                boolean isValid = this.validationUtils.isValid(country) &&
                        this.countryRepository.findFirstByCountryName(country.getCountryName()).isEmpty();

                if (isValid) {
                    stringBuilder.append(String.format(VALID_COUNTRY_FORMAT, country.getCountryName(), country.getCurrency()))
                            .append(System.lineSeparator());
                    this.countryRepository.saveAndFlush(mapper.map(country, Country.class));
                } else {
                    stringBuilder.append(INVALID_COUNTRY).append(System.lineSeparator());
                }
            }
        }

        return stringBuilder.toString();
    }
}
