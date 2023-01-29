package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartSeedDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PartServiceImpl implements PartService {

    private final String PARTS_JSON_PATH = "src/main/resources/files/json/parts.json";

    private final PartRepository partRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public PartServiceImpl(PartRepository partRepository,
                           ValidationUtil validationUtil,
                           ModelMapper mapper, Gson gson) {
        this.partRepository = partRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_JSON_PATH));
    }

    @Override
    public String importParts() throws IOException {
        final StringBuilder output = new StringBuilder();

        final PartSeedDTO[] partDTOs = this.gson.fromJson(readPartsFileContent(), PartSeedDTO[].class);

        Arrays.stream(partDTOs).filter(partDTO -> {

                    boolean isValid = this.validationUtil.isValid(partDTO) &&
                            this.partRepository.findByPartName(partDTO.getPartName()).isEmpty();

                    output.append(isValid ?
                                    String.format("Successfully imported part %s - %.2f",
                                            partDTO.getPartName(), partDTO.getPrice())
                                    : "Invalid part")
                            .append(System.lineSeparator());

                    return isValid;

                })
                .map(partDTO -> this.mapper.map(partDTO, Part.class))
                .forEach(partRepository::save);

        return output.toString();
    }
}
