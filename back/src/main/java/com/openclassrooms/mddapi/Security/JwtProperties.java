package com.openclassrooms.mddapi.Security;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Configuration properties class for JWT (JSON Web Token) settings.
 * This class loads JWT-related properties from the application configuration
 * (application.properties or application.yml) with the 'jwt' prefix.
 * It provides centralized configuration for JWT token generation and
 * validation.
 * 
 * The properties include:
 * <ul>
 * <li>Secret key for token signing and verification</li>
 * <li>Token expiration time</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
@Schema(description = "Configuration properties for JWT token generation and validation")
public class JwtProperties {

    @Schema(description = "Secret key for JWT signing and verification", example = "your-256-bit-secret-key-here")
    private String secret;

    @Schema(description = "Token expiration time in milliseconds", example = "86400000")
    private long expiration;
}