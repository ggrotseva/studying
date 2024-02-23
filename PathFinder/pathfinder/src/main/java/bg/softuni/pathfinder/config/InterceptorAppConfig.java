package bg.softuni.pathfinder.config;

import bg.softuni.pathfinder.web.interceptor.CustomInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Component
public class InterceptorAppConfig implements WebMvcConfigurer {

    CustomInterceptor customInterceptor;

    public InterceptorAppConfig(CustomInterceptor customInterceptor) {
        this.customInterceptor = customInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor);
    }
}
