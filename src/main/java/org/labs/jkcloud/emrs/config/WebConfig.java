package org.labs.jkcloud.emrs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for enabling CORS and static resource handling.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.origins}")
    private String CORS_ORIGIN; // Angular app's origin URL

    /**
     * Configure CORS mappings to allow Angular frontend to communicate with the backend.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(CORS_ORIGIN)  // Allow Angular's default port
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowCredentials(true)
                .allowedHeaders("*");
        // Allow credentials such as cookies or authorization headers
    }
}
