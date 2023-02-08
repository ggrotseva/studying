package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.entities.Role;
import bg.softuni.pathfinder.model.entities.User;
import bg.softuni.pathfinder.model.dto.UserLoginDTO;
import bg.softuni.pathfinder.model.dto.UserRegisterDTO;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.user.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private CurrentUser currentUser;

    public AuthService(UserRepository userRepository, RoleService roleService, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.currentUser = currentUser;
    }

    public boolean register(UserRegisterDTO registerDTO) {

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registerDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("email.used");
        }

        Optional<User> byUsername = this.userRepository.findByUsername(registerDTO.getUsername());

        if (byUsername.isPresent()) {
            throw new RuntimeException("username.used");
        }

        User user = new User(
                registerDTO.getUsername(),
                registerDTO.getPassword(),
                registerDTO.getEmail(),
                registerDTO.getFullName(),
                registerDTO.getAge())
                .setRoles(Set.of(this.roleService.findByName("USER")));

        this.userRepository.save(user);

        this.currentUser
                .setUsername(user.getUsername())
                .setLogged(true);

        return true;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());

        if (user.isEmpty()) {
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

    private void login(User user) {
        this.currentUser
                .setUsername(user.getUsername())
                .setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .setLogged(true);
    }

    public void logout() {
        this.currentUser.clear();
    }
}
