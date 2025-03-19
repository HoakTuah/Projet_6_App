package com.openclassrooms.mddapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entity representing a user in the system.
 * Implements UserDetails for Spring Security integration.
 * Contains user authentication and profile information.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "User entity containing authentication and profile information")
public class User implements UserDetails {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Integer id;

    /**
     * User's email address.
     * Must be unique in the system.
     */
    @Column(unique = true)
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    /**
     * User's chosen username.
     * Must be unique in the system.
     */
    @Column(unique = true)
    @Schema(description = "User's username", example = "johndoe")
    private String username;

    /**
     * User's encrypted password.
     * Not exposed in API responses.
     */
    @Schema(description = "User's encrypted password", hidden = true)
    private String password;

    /**
     * Timestamp when the user account was created.
     */
    @Column(name = "created_at")
    @Schema(description = "Timestamp when the user was created", example = "2024-03-19T10:30:00")
    private LocalDateTime createdAt;

    /**
     * Returns the authorities granted to the user.
     * All users have ROLE_USER authority by default.
     *
     * @return a list of granted authorities
     */
    @Override
    @Schema(description = "User's authorities/roles", hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Returns the email address used for authentication.
     * This method is used by Spring Security for authentication.
     *
     * @return the user's email address
     */
    @Override
    @Schema(description = "User's email used for authentication")
    public String getUsername() {
        return email;
    }

    /**
     * Returns the user's display username.
     * This is the username shown in the application UI.
     *
     * @return the user's chosen username
     */
    @Schema(description = "User's display username")
    public String getUsernameDisplay() {
        return username;
    }

    /**
     * Returns the user's encrypted password.
     *
     * @return the encrypted password
     */
    @Override
    @Schema(hidden = true)
    public String getPassword() {
        return password;
    }

    /**
     * Indicates whether the user's account has expired.
     * In this implementation, accounts never expire.
     *
     * @return true if the account is not expired
     */
    @Override
    @Schema(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or not.
     * In this implementation, accounts are never locked.
     *
     * @return true if the account is not locked
     */
    @Override
    @Schema(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials have expired.
     * In this implementation, credentials never expire.
     *
     * @return true if credentials are not expired
     */
    @Override
    @Schema(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * In this implementation, all users are enabled by default.
     *
     * @return true if the user is enabled
     */
    @Override
    @Schema(hidden = true)
    public boolean isEnabled() {
        return true;
    }
}