package bg.softuni.battleship.services;

import bg.softuni.battleship.models.User;
import bg.softuni.battleship.models.dto.UserLoginDTO;
import bg.softuni.battleship.models.dto.UserRegisterDTO;
import bg.softuni.battleship.repositories.UserRepository;
import bg.softuni.battleship.session.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final LoggedUser userSession;
    private final ModelMapper mapper;

    @Autowired
    public AuthService(UserRepository userRepository, LoggedUser userSession, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.userSession = userSession;
        this.mapper = mapper;
    }

    public boolean register(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return false;
        }

        User user = mapper.map(userRegisterDTO, User.class);

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

    public boolean isLoggedIn() {
        return getLoggedUserId() != null;
    }

    public Long getLoggedUserId() {
        return this.userSession.getId();
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }
}
