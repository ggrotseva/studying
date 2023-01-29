package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.ForecastExportDTO;
import softuni.exam.models.dto.ForecastImportDTO;
import softuni.exam.models.dto.ForecastImportWrapperDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.enums.DayOfWeek;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_FORECAST;
import static softuni.exam.constants.Messages.VALID_FORECAST_FORMAT;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository,
                               CityRepository cityRepository,
                               ValidationUtils validationUtils,
                               ModelMapper mapper, XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Paths.FORECASTS_XML_PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        final StringBuilder stringBuilder = new StringBuilder();

        if (!areImported()) {

            ForecastImportWrapperDTO wrapper = this.xmlParser.fromFile(ForecastImportWrapperDTO.class, Paths.FORECASTS_XML_PATH);

            for (ForecastImportDTO forecast : wrapper.getForecasts()) {
                boolean isValid = this.validationUtils.isValid(forecast);

                if (isValid) {
                    Optional<City> city = this.cityRepository.findFirstById(forecast.getCity());

                    if (city.isPresent()) {

                        stringBuilder.append(
                                        String.format(VALID_FORECAST_FORMAT, forecast.getDayOfWeek(), forecast.getMaxTemperature()))
                                .append(System.lineSeparator());

                        Forecast forecastToSave = this.mapper.map(forecast, Forecast.class);

                        forecastToSave.setCity(city.get());

                        this.forecastRepository.saveAndFlush(forecastToSave);
                    }
                } else {
                    stringBuilder.append(INVALID_FORECAST).append(System.lineSeparator());
                }
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public String exportForecasts() {
        List<ForecastExportDTO> forecasts = this.forecastRepository.findAllByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescIdAsc
                (DayOfWeek.SUNDAY, 150000)
                .stream()
                .map(f -> mapper.map(f, ForecastExportDTO.class))
                .collect(Collectors.toList());

        return forecasts.stream().map(ForecastExportDTO::toString).collect(Collectors.joining(System.lineSeparator()));
    }
}
