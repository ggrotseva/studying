package bg.softuni.mobilelele.service.userRole;

import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.model.userRole.UserRole;
import bg.softuni.mobilelele.model.userRole.dto.UserRoleViewDTO;
import bg.softuni.mobilelele.repository.UserRoleRepository;
import bg.softuni.mobilelele.service.DatabaseInitService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleService implements DatabaseInitService {

    private final UserRoleRepository userRoleRepository;
    private final ModelMapper mapper;

    public UserRoleService(UserRoleRepository userRoleRepository, ModelMapper mapper) {
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void postConstruct() {
        dbInit();
    }

    @Override
    public void dbInit() {
        if (!isDbInit()) {
            Arrays.stream(UserRoleEnum.values()).forEach(
                    role -> this.userRoleRepository.saveAndFlush(new UserRole().setRole(role))
            );
        }
    }

    @Override
    public boolean isDbInit() {
        return this.userRoleRepository.count() > 0;
    }

    public List<UserRoleViewDTO> getRoles() {
        return this.userRoleRepository.findAll().stream()
                .map(role -> this.mapper.map(role, UserRoleViewDTO.class))
                .toList();
    }
}
