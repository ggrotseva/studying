package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.model.entities.UserRole;
import bg.softuni.mobilelele.model.dto.UserRoleViewDTO;
import bg.softuni.mobilelele.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

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

    public List<UserRole> getRoles() {
        return this.userRoleRepository.findAll().stream()
//                .map(role -> this.mapper.map(role, UserRoleViewDTO.class))
                .toList();
    }

    public UserRole findByRole(String userRole) {
        return this.userRoleRepository.findByRole(UserRoleEnum.valueOf(userRole))
                .orElseThrow(NoSuchElementException::new);
    }
}
