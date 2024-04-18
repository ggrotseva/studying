package softuni.expirationManager.service.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import softuni.expirationManager.service.UserService;

import java.io.IOException;

@Component
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    public OAuthSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (authentication instanceof OAuth2AuthenticationToken token) {
            OAuth2User user = token.getPrincipal();

            String username = user.getAttribute("login");
            String names = user.getAttribute("name");
            String email = user.getAttribute("email");

            this.userService.createUserIfNotExist(email, username, names);
            authentication = this.userService.login(username);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
