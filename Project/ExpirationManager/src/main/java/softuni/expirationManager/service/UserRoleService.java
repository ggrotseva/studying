package softuni.expirationManager.service;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.UserRoleEntity;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.UserRoleRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleService {

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

    public void dbInit() {
        if (this.userRoleRepository.count() == 0) {
            Arrays.stream(UserRoleEnum.values()).forEach(
                    role -> this.userRoleRepository.saveAndFlush(new UserRoleEntity().setRole(role))
            );
        }
    }

    public List<UserRoleEntity> getRoles() {
        return this.userRoleRepository.findAll().stream()
//                .map(role -> this.mapper.map(role, UserRoleViewDTO.class))
                .toList();
    }
}
