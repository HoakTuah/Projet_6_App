package com.openclassrooms.mddapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a user in the system.
 * Implements UserDetails for Spring Security integration.
 * Contains user authentication and profile information.
 * Manages topic subscriptions through a many-to-many relationship.
 * 
 * Features:
 * <ul>
 * <li>User authentication and authorization</li>
 * <li>Topic subscription management</li>
 * <li>Profile information storage</li>
 * <li>Builder pattern for object creation</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = "subscribedTopics")
@Schema(description = "User entity containing authentication and profile information")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Integer id;

    @Column(unique = true)
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @Column(unique = true)
    @Schema(description = "User's username", example = "johndoe")
    private String username;

    @Schema(description = "User's encrypted password", hidden = true)
    private String password;

    @Column(name = "created_at")
    @Schema(description = "Timestamp when the user was created", example = "2024-03-19T10:30:00")
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "subscribers")
    @Schema(description = "Topics that the user has subscribed to")
    private Set<Topic> subscribedTopics = new HashSet<>();

    /**
     * Custom equals implementation to prevent infinite recursion.
     * Compares users based on their ID.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return id != null && id.equals(user.getId());
    }

    /**
     * Custom hashCode implementation to prevent infinite recursion.
     * Uses the class type for hashing.
     *
     * @return The hash code of the object
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Adds a topic to the user's subscriptions.
     * Maintains bidirectional relationship between User and Topic.
     *
     * @param topic The topic to subscribe to
     */
    public void addSubscribedTopic(Topic topic) {
        subscribedTopics.add(topic);
        topic.getSubscribers().add(this);
    }

    /**
     * Removes a topic from the user's subscriptions.
     * Maintains bidirectional relationship between User and Topic.
     *
     * @param topic The topic to unsubscribe from
     */
    public void removeSubscribedTopic(Topic topic) {
        subscribedTopics.remove(topic);
        topic.getSubscribers().remove(this);
    }

    /**
     * Returns the user's authorities/roles.
     * Currently implements a simple role-based system with ROLE_USER.
     *
     * @return Collection of granted authorities
     */
    @Override
    @Schema(description = "User's authorities/roles", hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Returns the user's email for authentication purposes.
     * This is the primary identifier used for authentication.
     *
     * @return The user's email address
     */
    @Override
    @Schema(description = "User's email used for authentication")
    public String getUsername() {
        return email;
    }

    /**
     * Returns the user's display username.
     * This is different from the authentication username (email).
     *
     * @return The user's display username
     */
    @Schema(description = "User's display username")
    public String getUsernameDisplay() {
        return username;
    }

    /**
     * Returns the user's encrypted password.
     * Hidden from API documentation for security.
     *
     * @return The user's encrypted password
     */
    @Override
    @Schema(hidden = true)
    public String getPassword() {
        return password;
    }

    /**
     * Checks if the user's account is not expired.
     * Currently always returns true as account expiration is not implemented.
     *
     * @return true if the account is not expired
     */
    @Override
    @Schema(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if the user's account is not locked.
     * Currently always returns true as account locking is not implemented.
     *
     * @return true if the account is not locked
     */
    @Override
    @Schema(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if the user's credentials are not expired.
     * Currently always returns true as credential expiration is not implemented.
     *
     * @return true if the credentials are not expired
     */
    @Override
    @Schema(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if the user's account is enabled.
     * Currently always returns true as account disabling is not implemented.
     *
     * @return true if the account is enabled
     */
    @Override
    @Schema(hidden = true)
    public boolean isEnabled() {
        return true;
    }

    /**
     * Default constructor.
     * Initializes the subscribedTopics set.
     */
    public User() {
        this.subscribedTopics = new HashSet<>();
    }

    /**
     * Creates a new UserBuilder instance.
     *
     * @return A new UserBuilder instance
     */
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    /**
     * Builder class for creating User instances.
     * Provides a fluent interface for setting user properties.
     */
    public static class UserBuilder {
        private final User user;

        /**
         * Creates a new UserBuilder with an empty User instance.
         */
        public UserBuilder() {
            user = new User();
        }

        /**
         * Sets the user's ID.
         *
         * @param id The user's ID
         * @return The builder instance for method chaining
         */
        public UserBuilder id(Integer id) {
            user.setId(id);
            return this;
        }

        /**
         * Sets the user's email.
         *
         * @param email The user's email
         * @return The builder instance for method chaining
         */
        public UserBuilder email(String email) {
            user.setEmail(email);
            return this;
        }

        /**
         * Sets the user's username.
         *
         * @param username The user's username
         * @return The builder instance for method chaining
         */
        public UserBuilder username(String username) {
            user.setUsername(username);
            return this;
        }

        /**
         * Sets the user's password.
         *
         * @param password The user's password
         * @return The builder instance for method chaining
         */
        public UserBuilder password(String password) {
            user.setPassword(password);
            return this;
        }

        /**
         * Sets the user's creation timestamp.
         *
         * @param createdAt The creation timestamp
         * @return The builder instance for method chaining
         */
        public UserBuilder createdAt(LocalDateTime createdAt) {
            user.setCreatedAt(createdAt);
            return this;
        }

        /**
         * Sets the user's subscribed topics.
         *
         * @param subscribedTopics The set of subscribed topics
         * @return The builder instance for method chaining
         */
        public UserBuilder subscribedTopics(Set<Topic> subscribedTopics) {
            user.setSubscribedTopics(subscribedTopics);
            return this;
        }

        /**
         * Builds and returns the User instance.
         *
         * @return The constructed User instance
         */
        public User build() {
            return user;
        }
    }
}