package mn.clinic.supplies.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull; // Import NonNull annotation

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) { // Adding @NonNull
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500") // The frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
