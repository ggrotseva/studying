package bg.softuni.pathfinder.model.config;

import bg.softuni.pathfinder.model.enums.UserRole;
import bg.softuni.pathfinder.service.AuthService;
import bg.softuni.pathfinder.service.PathfinderUserDetailsService;
import bg.softuni.pathfinder.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.
                authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/", "/login", "/register","/about", "/login-error").permitAll()
                .requestMatchers("/pages/moderator").hasRole(UserRole.MODERATOR.name())
                .requestMatchers("/pages/admin").hasRole(UserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .failureForwardUrl("/login-error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new PathfinderUserDetailsService(userService);
    }
}


