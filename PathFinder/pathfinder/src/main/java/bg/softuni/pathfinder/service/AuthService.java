package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.entities.Role;
import bg.softuni.pathfinder.model.entities.User;
import bg.softuni.pathfinder.model.dto.UserLoginDTO;
import bg.softuni.pathfinder.model.dto.UserRegisterDTO;
import bg.softuni.pathfinder.model.enums.Level;
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

    public void register(UserRegisterDTO registerDTO) {

        User user = new User(
                registerDTO.getUsername(),
                registerDTO.getPassword(),
                registerDTO.getEmail(),
                registerDTO.getFullName(),
                registerDTO.getAge())
                .setLevel(Level.BEGINNER)
                .setRoles(Set.of(this.roleService.findByName("USER"))
                );

        this.userRepository.save(user);
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
        this.currentUser.login(user);
    }

    public void logout() {
        this.currentUser.clear();
    }
}
