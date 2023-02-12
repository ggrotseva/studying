package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.dto.UserLoginDTO;
import bg.softuni.mobilelele.model.dto.UserRegisterDTO;
import bg.softuni.mobilelele.model.dto.UserRoleViewDTO;
import bg.softuni.mobilelele.model.entities.User;
import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.user.CurrentUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements DatabaseInitService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static final String USER_ROLE = "USER";

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final ModelMapper mapper;

    private CurrentUser currentUser;

    public UserService(UserRepository userRepository,
                       UserRoleService userRoleService,
                       ModelMapper mapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.mapper = mapper;
        this.currentUser = currentUser;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.userRepository.count() > 0;
    }

    public void register(UserRegisterDTO userRegisterDTO) {

        User newUser = mapper.map(userRegisterDTO, User.class)
                .setActive(true)
                .setCreated(LocalDateTime.now())
                .setUserRoles(this.userRepository.count() == 0 ?
                        this.userRoleService.getRoles() :
                        List.of(this.userRoleService.findByRole(USER_ROLE)));

        userRepository.save(newUser);

    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findByUsername(userLoginDTO.getUsername());

        if (user.isEmpty()) {
            LOGGER.info("User with name [{}] not found.", userLoginDTO.getUsername());
            return false;
        }

        boolean success = user.get().getPassword().equals(userLoginDTO.getPassword());

        if (success) {
            login(user.get());
        }

        return success;
    }

    private void login(User user) {
        this.currentUser
                .setLoggedIn(true)
                .setUsername(user.getUsername())
                .setRole(user.getUserRoles().stream()
                        .map(r -> mapper.map(r, UserRoleViewDTO.class))
                        .collect(Collectors.toList()));
    }

    public void logout() {
        this.currentUser.clear();
    }

}
