package com.openclassrooms.mddapi.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service for handling JWT (JSON Web Token) operations in the application.
 * This service provides functionality for token generation, validation, and
 * management.
 * 
 * Features:
 * <ul>
 * <li>Token generation with custom claims</li>
 * <li>Token validation and expiration checking</li>
 * <li>Username extraction from tokens</li>
 * <li>Secure token signing using HS256 algorithm</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Tag(name = "JWT Service", description = "Handles JWT token operations")
public class JwtService {

    private final JwtProperties jwtProperties;

    /**
     * Generates a JWT token for a user with default claims.
     * The token includes:
     * <ul>
     * <li>User's username as subject</li>
     * <li>Current timestamp as issued at</li>
     * <li>Expiration time based on configured duration</li>
     * </ul>
     *
     * @param userDetails The user details to generate the token for
     * @return The generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional custom claims.
     * The token includes:
     * <ul>
     * <li>All provided extra claims</li>
     * <li>User's username as subject</li>
     * <li>Current timestamp as issued at</li>
     * <li>Expiration time based on configured duration</li>
     * </ul>
     *
     * @param extraClaims Additional claims to include in the token
     * @param userDetails The user details to generate the token for
     * @return The generated JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     * The username is stored in the token's subject claim.
     *
     * @param token The JWT token to extract the username from
     * @return The username stored in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates a JWT token against user details.
     * Checks both the token's validity and expiration.
     *
     * @param token       The JWT token to validate
     * @param userDetails The user details to validate against
     * @return true if the token is valid and not expired, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if a token has expired.
     *
     * @param token The JWT token to check
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token The JWT token to extract the expiration from
     * @return The expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from a JWT token using a claims resolver function.
     *
     * @param token          The JWT token to extract the claim from
     * @param claimsResolver The function to resolve the specific claim
     * @param <T>            The type of the claim to extract
     * @return The extracted claim value
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a JWT token.
     * Uses the configured secret key to verify the token's signature.
     *
     * @param token The JWT token to extract claims from
     * @return The claims contained in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}