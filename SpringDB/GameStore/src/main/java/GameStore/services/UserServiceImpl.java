package GameStore.services;

import GameStore.domain.dtos.UserRegisterDTO;
import GameStore.domain.entities.Game;
import GameStore.domain.entities.User;
import GameStore.repositories.GameRepository;
import GameStore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static GameStore.constants.Validations.*;

@Service
public class UserServiceImpl implements UserService {

    public static final String LOGOUT_USER_FORMAT = "User %s successfully logged out";

    private User loggedInUser;

    private final ModelMapper mapper;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper mapper, GameRepository gameRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String[] tokens) {
        final String email = tokens[1];
        final String password = tokens[2];
        final String confirmPassword = tokens[3];
        final String fullName = tokens[4];

        final boolean isSuchUserPresent = this.userRepository.findByEmail(email).isPresent();

        if (isSuchUserPresent) {
//            throw new IllegalArgumentException(EMAIL_EXISTS_MESSAGE);
            return EMAIL_EXISTS_MESSAGE;
        }

        final UserRegisterDTO registerUser;

        try {
            registerUser = new UserRegisterDTO(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        final User user = mapper.map(registerUser, User.class);

        if (this.userRepository.count() == 0) {
            user.setIsAdmin(true);
        }

        this.userRepository.save(user);

        return registerUser.successfulRegisterMessage();
    }

    @Override
    public String loginUser(String[] tokens) {
        final String email = tokens[1];
        final String password = tokens[2];

        Optional<User> foundUser = this.userRepository.findByEmail(email);

        if (foundUser.isPresent() &&
                foundUser.get().getPassword().equals(password) &&
                this.loggedInUser == null) {

            this.loggedInUser = foundUser.get();

            return "Successfully logged in " + this.loggedInUser.getFullName();
        }

//        throw new IllegalArgumentException(INCORRECT_USERNAME_OR_PASSWORD_MESSAGE);
        return INCORRECT_USERNAME_OR_PASSWORD_MESSAGE;
    }

    @Override
    public String logoutUser() {
        if (this.loggedInUser == null) {
            return CANNOT_LOGOUT_MESSAGE + NO_LOGGED_USER;
        }

        final String logoutMessage = String.format(LOGOUT_USER_FORMAT, this.loggedInUser.getFullName());

        this.loggedInUser = null;

        return logoutMessage;
    }

    @Override
    public String viewOwnedGames() {
        if (this.loggedInUser == null) {
            return NO_LOGGED_USER;
        }

        return this.loggedInUser.getGames().stream().map(Game::getTitle).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

}
