package softuni.expirationManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import softuni.expirationManager.model.MyUserDetails;
import softuni.expirationManager.model.entities.UserEntity;
import softuni.expirationManager.model.entities.UserRoleEntity;
import softuni.expirationManager.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return map(this.userRepository.findByUsername(username).orElseThrow());
    }

    private UserDetails map(UserEntity userEntity) {
        return new MyUserDetails(userEntity.getUsername(),
                userEntity.getPassword(),
                extractGrantedAuthorities(userEntity))
                .setId(userEntity.getId());
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
