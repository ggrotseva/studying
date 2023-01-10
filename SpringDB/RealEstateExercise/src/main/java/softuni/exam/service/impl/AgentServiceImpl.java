package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.constants.FilePaths;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.constants.ValidationMessages.INVALID_AGENT_MESSAGE;
import static softuni.exam.constants.ValidationMessages.SUCCESSFULLY_IMPORTED_AGENT_FORMAT;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository,
                            ValidationUtil validationUtil,
                            ModelMapper mapper, Gson gson) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(FilePaths.AGENTS_JSON_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        final StringBuilder outputBuilder = new StringBuilder();

        final AgentImportDTO[] agentImportDTOS = this.gson.fromJson(readAgentsFromFile(), AgentImportDTO[].class);

        for (AgentImportDTO agent : agentImportDTOS) {

            if (!validationUtil.isValid(agent) || this.agentRepository.findByFirstName(agent.getFirstName()).isPresent()) {
                outputBuilder.append(INVALID_AGENT_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Optional<Town> town = this.townRepository.findByTownName(agent.getTown());

            if (town.isEmpty()) {
                outputBuilder.append(INVALID_AGENT_MESSAGE).append(System.lineSeparator());
            }

            Agent agentToAdd = this.mapper.map(agent, Agent.class);
            agentToAdd.setTown(town.get());

            this.agentRepository.save(agentToAdd);

            outputBuilder.append(String.format(SUCCESSFULLY_IMPORTED_AGENT_FORMAT, agent.getFirstName(), agent.getLastName()))
                    .append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }
}
