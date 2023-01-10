package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constants.FilePaths.TOWNS_JSON_PATH;
import static softuni.exam.constants.ValidationMessages.INVALID_TOWN_MESSAGE;
import static softuni.exam.constants.ValidationMessages.SUCCESSFULLY_IMPORTED_TOWN_FORMAT;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil,
                           ModelMapper mapper, Gson gson) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_JSON_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        final StringBuilder outputBuilder = new StringBuilder();

        final TownImportDTO[] townImportDTOS = this.gson.fromJson(readTownsFileContent(), TownImportDTO[].class);

        for (TownImportDTO town : townImportDTOS) {

            if (!this.validationUtil.isValid(town) || this.townRepository.findByTownName(town.getTownName()).isPresent()) {
                outputBuilder.append(INVALID_TOWN_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Town townToAdd = this.mapper.map(town, Town.class);
            this.townRepository.save(townToAdd);

            outputBuilder.append(String.format(SUCCESSFULLY_IMPORTED_TOWN_FORMAT, town.getTownName(), town.getPopulation()))
                    .append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }
}
