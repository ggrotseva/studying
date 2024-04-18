package softuni.expirationManager.config;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import softuni.expirationManager.model.enums.UserRoleEnum;
import softuni.expirationManager.repository.UserRepository;
import softuni.expirationManager.service.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import softuni.expirationManager.service.oauth.OAuthSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, OAuthSuccessHandler oAuthSuccessHandler) throws Exception {

        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/about", "/users/login", "/users/register", "/users/login-error").permitAll()
                        .requestMatchers("/admin/**").hasRole(UserRoleEnum.ADMIN.name())
                        .anyRequest().authenticated())
                .formLogin(customize -> customize.loginPage("/users/login")
                        .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                        .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                        .defaultSuccessUrl("/")
                        .failureForwardUrl("/users/login-error"))
                .rememberMe(customize -> customize.rememberMeParameter("remember-me"))
                .logout(customize -> customize
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID", "remember-me")
                        .invalidateHttpSession(true))
                .oauth2Login(customizer -> customizer.successHandler(oAuthSuccessHandler));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ApplicationUserDetailsService(userRepository);
    }
}

