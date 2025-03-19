package com.openclassrooms.mddapi.Configuration;

import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for application-wide authentication settings.
 * Provides beans for Spring Security authentication mechanisms including
 * user details service, password encoding, and authentication management.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * Creates a UserDetailsService bean for loading user-specific data.
     * Uses email as the username for authentication.
     *
     * @return UserDetailsService implementation that fetches user details from the
     *         database
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    /**
     * Creates an AuthenticationProvider bean that uses the UserDetailsService
     * and PasswordEncoder for authenticating users.
     *
     * @return DaoAuthenticationProvider configured with custom UserDetailsService
     *         and PasswordEncoder
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates an AuthenticationManager bean using the provided configuration.
     * This manager is responsible for processing authentication requests.
     *
     * @param config the authentication configuration to use
     * @return AuthenticationManager instance
     * @throws Exception if the authentication manager cannot be created
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates a PasswordEncoder bean for encoding and verifying passwords.
     * Uses BCrypt hashing algorithm for secure password storage.
     *
     * @return BCryptPasswordEncoder instance for password encoding
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}