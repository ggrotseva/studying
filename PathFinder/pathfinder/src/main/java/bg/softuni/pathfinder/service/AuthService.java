package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.dto.UserRegisterDTO;
import bg.softuni.pathfinder.model.entities.UserEntity;
import bg.softuni.pathfinder.model.enums.Level;
import bg.softuni.pathfinder.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public AuthService(UserRepository userRepository,
                       RoleService roleService,
                       PasswordEncoder passwordEncoder,
                       ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public void register(UserRegisterDTO userRegisterDTO) {

        UserEntity newUser = mapper.map(userRegisterDTO, UserEntity.class);

        newUser.setLevel(Level.BEGINNER)
                .setRoles(Set.of(this.roleService.findByName("USER")))
                .setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        this.userRepository.save(newUser);
    }

}
