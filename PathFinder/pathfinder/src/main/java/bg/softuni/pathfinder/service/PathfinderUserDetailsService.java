package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.entities.Role;
import bg.softuni.pathfinder.model.entities.UserEntity;
import bg.softuni.pathfinder.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class PathfinderUserDetailsService implements UserDetailsService {

    private final AuthService authService;

    public PathfinderUserDetailsService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return map(this.authService.findByUsername(username));
    }

    private UserDetails map(UserEntity userEntity) {
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                extractGrantedAuthorities(userEntity));
    }

    private List<GrantedAuthority> extractGrantedAuthorities(UserEntity userEntity) {
        return userEntity.getRoles()
                .stream()
                .map(this::mapRoles)
                .toList();
    }

    private GrantedAuthority mapRoles(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName().name());
    }
}
