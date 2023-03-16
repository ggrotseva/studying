package softuni.expirationManager.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.expirationManager.model.dtos.user.UserRegisterDTO;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.model.entities.UserRoleEntity;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.UserRepository;
import softuni.expirationManager.repository.UserRoleRepository;

import java.io.IOException;
import java.util.List;

@Service
public class AuthService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public AuthService(UserRoleRepository userRoleRepository,
                       UserRepository userRepository,
                       CategoryService categoryService,
                       PasswordEncoder passwordEncoder,
                       ModelMapper mapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public void register(UserRegisterDTO userRegisterDTO) throws IOException {
        UserEntity newUser = mapper.map(userRegisterDTO, UserEntity.class);

        List<UserRoleEntity> allRoles = this.userRepository.count() == 0 ?
                this.userRoleRepository.findAll() :
                List.of(this.userRoleRepository.findByRole(UserRoleEnum.USER).orElseThrow());

        newUser.setUserRoles(allRoles)
                .setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        this.userRepository.saveAndFlush(newUser);

        this.categoryService.initStartCategories(newUser);
    }
}
