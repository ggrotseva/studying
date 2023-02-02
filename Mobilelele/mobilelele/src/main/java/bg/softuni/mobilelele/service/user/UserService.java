package bg.softuni.mobilelele.service.user;

import bg.softuni.mobilelele.demo.CurrentUser;
import bg.softuni.mobilelele.model.user.User;
import bg.softuni.mobilelele.model.user.dto.UserLoginDTO;
import bg.softuni.mobilelele.model.user.dto.UserRegisterDTO;
import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.service.DatabaseInitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements DatabaseInitService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private CurrentUser currentUser;

    public UserService(UserRepository userRepository, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    @Override
    public void dbInit() {

    }

    @Override
    public boolean isDbInit() {
        return this.userRepository.count() > 0;
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {

        User newUser = new User()
                .setActive(true)
                .setCreated(LocalDateTime.now())
                .setUsername(userRegisterDTO.getUsername())
                .setPassword(userRegisterDTO.getPassword())
                .setFirstName(userRegisterDTO.getFirstName())
                .setLastName(userRegisterDTO.getLastName());

        userRepository.save(newUser);

        login(newUser);
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findByUsername(userLoginDTO.getUsername());

        if (user.isEmpty()) {
            LOGGER.debug("User with name [{}] not found.", userLoginDTO.getUsername());
            return false;
        }

        boolean success = user.get().getPassword().equals(userLoginDTO.getPassword());

        if (success) {
            login(user.get());
        } else {
            logout();
        }

        return success;
    }

    public void login(User user) {
        // TODO

    }

    public void logout() {
        this.currentUser = null;
    }

}
