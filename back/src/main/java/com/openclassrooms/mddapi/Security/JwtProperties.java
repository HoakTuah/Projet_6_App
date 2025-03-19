package com.openclassrooms.mddapi.Security;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Configuration properties for JWT authentication.
 * Loads properties with 'jwt' prefix from application.properties.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
@Schema(description = "JWT configuration properties")
public class JwtProperties {

    @Schema(description = "Secret key for JWT signing")
    private String secret;

    @Schema(description = "Token expiration time in milliseconds")
    private long expiration;
}