package bg.softuni.mobilelele.config;

import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.repository.UserRepository;
import bg.softuni.mobilelele.service.ApplicationUserDetails;
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
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // set pages accessible only by GUEST
                .requestMatchers("/", "/users/login", "/users/register", "/users/login-error").permitAll()
                // set pages accessible only by MODERATOR
                .requestMatchers("/pages/moderator").hasRole(UserRoleEnum.MODERATOR.name())
                // set pages accessible only by ADMIN
                .requestMatchers("/pages/admin").hasRole(UserRoleEnum.ADMIN.name())
                // all other pages: AUTHENTICATION REQUIRED !
                .anyRequest().authenticated()
                .and()
                // set login page with credentials' names
                .formLogin().loginPage("/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                // success URL redirect
                .defaultSuccessUrl("/", true)
                // not successful URL redirect
                .failureForwardUrl("/users/login-error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true);

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ApplicationUserDetails(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}