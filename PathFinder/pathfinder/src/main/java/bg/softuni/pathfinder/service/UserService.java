package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.dto.UserDetailsDTO;
import bg.softuni.pathfinder.model.entities.Role;
import bg.softuni.pathfinder.model.entities.User;
import bg.softuni.pathfinder.model.enums.UserRole;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.user.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CurrentUser currentUser;

    public UserService(UserRepository userRepository, RoleService roleService, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.currentUser = currentUser;
    }

    public UserDetailsDTO getUserProfile() {
        User user = this.userRepository.findByUsername(currentUser.getUsername()).get();

        return new UserDetailsDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFullName(user.getFullName())
                .setAge(user.getAge())
                .setLevel(user.getLevel());
    }

    public Set<UserRole> addRole(Long id, boolean shouldReplaceRoles, String roleName) {

        User user = this.userRepository.findById(id).orElseThrow(NoSuchElementException::new);

        Set<Role> roles = user.getRoles();

        if (shouldReplaceRoles) {
            user.setRoles(Set.of(this.roleService.findByName(roleName)));
        } else {
            roles.add(this.roleService.findByName(roleName));
        }

        user.setRoles(roles);
        this.userRepository.save(user);

        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
