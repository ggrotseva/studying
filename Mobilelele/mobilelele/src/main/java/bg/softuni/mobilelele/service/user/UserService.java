package bg.softuni.mobilelele.service.user;

import bg.softuni.mobilelele.model.user.User;
import bg.softuni.mobilelele.model.user.dto.UserLoginDTO;
import bg.softuni.mobilelele.model.user.dto.UserRegisterDTO;
import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.service.DatabaseInitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements DatabaseInitService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                .setFirstName(userRegisterDTO.getFirstName())
                .setLastName(userRegisterDTO.getLastName())
                .setUsername(userRegisterDTO.getUsername())
                .setPassword(userRegisterDTO.getPassword());

        this.userRepository.save(newUser);
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findByUsername(userLoginDTO.getUsername());

        if (user.isEmpty()) {
            LOGGER.debug("User with name [{}] not found.", userLoginDTO.getUsername());
            return false;
        }

        return user.get().getPassword().equals(userLoginDTO.getPassword());
    }
}
