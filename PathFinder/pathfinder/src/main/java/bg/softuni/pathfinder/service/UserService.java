package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.dto.UserDetailsDTO;
import bg.softuni.pathfinder.model.entities.Role;
import bg.softuni.pathfinder.model.entities.UserEntity;
import bg.softuni.pathfinder.model.enums.UserRole;
import bg.softuni.pathfinder.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper mapper;

    public UserService(UserRepository userRepository,
                       RoleService roleService, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    public UserDetailsDTO getUserProfile(String username) {
        return this.mapper.map(this.userRepository.findByUsername(username).get(), UserDetailsDTO.class);
    }

    // SHOULD RETURN DTO!
    public UserEntity findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
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
