package com.openclassrooms.mddapi.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS) settings.
 * Enables and configures CORS for the application to allow frontend access.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */

@Configuration
public class CorsConfig {

    /**
     * Creates and configures a CORS filter bean.
     * This filter allows cross-origin requests from specified origins
     * with defined methods, headers, and other CORS settings.
     * 
     * @return A configured CorsFilter instance
     */

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // allow requests from frontend
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

        // allow HTTP methods
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // allow all headers
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With",
                "Accept", "Origin", "Access-Control-Request-Method",
                "Access-Control-Request-Headers"));

        // allow cookies
        corsConfiguration.setAllowCredentials(true);

        // expose response headers
        corsConfiguration
                .setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));

        // cache CORS configuration for 30 minutes
        corsConfiguration.setMaxAge(1800L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}