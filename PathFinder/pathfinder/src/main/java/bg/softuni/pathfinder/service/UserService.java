package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.dto.UserDetailsDTO;
import bg.softuni.pathfinder.model.entities.Role;
import bg.softuni.pathfinder.model.entities.UserEntity;
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

    public UserService(UserRepository userRepository,
                       RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public UserDetailsDTO getUserProfile(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username).get();

        return new UserDetailsDTO()
                .setId(userEntity.getId())
                .setUsername(userEntity.getUsername())
                .setFullName(userEntity.getFullName())
                .setAge(userEntity.getAge())
                .setLevel(userEntity.getLevel());
    }

    public Set<UserRole> addRole(Long id, boolean shouldReplaceRoles, String roleName) {

        UserEntity userEntity = this.userRepository.findById(id).orElseThrow(NoSuchElementException::new);

        Set<Role> roles = userEntity.getRoles();

        if (shouldReplaceRoles) {
            userEntity.setRoles(Set.of(this.roleService.findByName(roleName)));
        } else {
            roles.add(this.roleService.findByName(roleName));
        }

        userEntity.setRoles(roles);
        this.userRepository.save(userEntity);

        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
