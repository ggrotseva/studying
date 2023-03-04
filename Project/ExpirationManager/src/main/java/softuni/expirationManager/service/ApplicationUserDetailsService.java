package softuni.expirationManager.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.expirationManager.model.UserEntity;
import softuni.expirationManager.model.UserRoleEntity;
import softuni.expirationManager.repository.UserRepository;

import java.util.List;

public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow();
    }

    private UserDetails map(UserEntity userEntity) {
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                extractGrantedAuthorities(userEntity));
    }

    private List<GrantedAuthority> extractGrantedAuthorities(UserEntity userEntity) {
        return userEntity.getUserRoles()
                .stream()
                .map(this::mapRoles)
                .toList();
    }

    private GrantedAuthority mapRoles(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getRole().name());
    }
}
