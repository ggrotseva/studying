package bg.softuni.mobilelele.service;

import bg.softuni.mobilelele.model.entities.UserEntity;
import bg.softuni.mobilelele.model.entities.UserRole;
import bg.softuni.mobilelele.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).map(this::map).orElseThrow();
    }

    private UserDetails map(UserEntity user) {
        return new User(user.getUsername(),
                user.getPassword(),
                getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(UserEntity user) {
        return user.getUserRoles().stream().map(this::mapRole).collect(Collectors.toList());
    }

    private GrantedAuthority mapRole(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getRole().name());
    }
}
