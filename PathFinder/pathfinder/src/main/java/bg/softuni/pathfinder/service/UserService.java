package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Role;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.model.enums.UserRole;
import bg.softuni.pathfinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
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
