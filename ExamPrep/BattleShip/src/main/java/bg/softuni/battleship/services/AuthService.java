package bg.softuni.battleship.services;

import bg.softuni.battleship.models.User;
import bg.softuni.battleship.models.dto.UserLoginDTO;
import bg.softuni.battleship.models.dto.UserRegisterDTO;
import bg.softuni.battleship.repositories.UserRepository;
import bg.softuni.battleship.session.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final LoggedUser userSession;

    @Autowired
    public AuthService(UserRepository userRepository, LoggedUser userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean register(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return false;
        }

        Optional<User> byUsername = this.userRepository.findByUsername(userRegisterDTO.getUsername());

        if (byUsername.isPresent()) {
            return false;
        }

        Optional<User> byEmail = this.userRepository.findByEmail(userRegisterDTO.getEmail());

        if (byEmail.isPresent()) {
            return false;
        }

        User user = new User()
                .setUsername(userRegisterDTO.getUsername())
                .setFullName(userRegisterDTO.getFullName())
                .setEmail(userRegisterDTO.getEmail())
                .setPassword(userRegisterDTO.getPassword());

        this.userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> user = this.userRepository
                .findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        if (user.isEmpty()) {
            return false;
        }

        this.userSession.login(user.get());

        return true;
    }

    public void logout() {
        this.userSession.logout();
    }
}
