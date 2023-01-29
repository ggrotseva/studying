package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicSeedDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class MechanicServiceImpl implements MechanicService {

    private final String MECHANICS_JSON_PATH = "src/main/resources/files/json/mechanics.json";

    private final MechanicRepository mechanicRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository,
                               ValidationUtil validationUtil,
                               ModelMapper mapper, Gson gson) {
        this.mechanicRepository = mechanicRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_JSON_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        final StringBuilder output = new StringBuilder();

        final MechanicSeedDTO[] mechanicDTOs = this.gson.fromJson(readMechanicsFromFile(), MechanicSeedDTO[].class);

        Arrays.stream(mechanicDTOs)
                .filter(mechanicDTO -> {
                    boolean isValid = this.validationUtil.isValid(mechanicDTO)
                            && this.mechanicRepository.findByEmail(mechanicDTO.getEmail()).isEmpty();

                    output.append(isValid ? String.format("Successfully imported mechanic %s %s",
                                    mechanicDTO.getFirstName(), mechanicDTO.getLastName())
                                    : "Invalid mechanic")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(mechanicDTO -> this.mapper.map(mechanicDTO, Mechanic.class))
                .forEach(mechanicRepository::save);

        return output.toString();
    }
}
