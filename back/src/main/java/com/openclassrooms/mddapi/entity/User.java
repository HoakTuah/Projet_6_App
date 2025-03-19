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

    // Custom equals and hashCode methods to prevent infinite recursion
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return id != null && id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // Helper methods for managing subscriptions
    public void addSubscribedTopic(Topic topic) {
        subscribedTopics.add(topic);
        topic.getSubscribers().add(this);
    }

    public void removeSubscribedTopic(Topic topic) {
        subscribedTopics.remove(topic);
        topic.getSubscribers().remove(this);
    }

    // UserDetails implementation methods
    @Override
    @Schema(description = "User's authorities/roles", hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @Schema(description = "User's email used for authentication")
    public String getUsername() {
        return email; // Using email for authentication
    }

    @Schema(description = "User's display username")
    public String getUsernameDisplay() {
        return username;
    }

    @Override
    @Schema(hidden = true)
    public String getPassword() {
        return password;
    }

    @Override
    @Schema(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Schema(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Schema(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Schema(hidden = true)
    public boolean isEnabled() {
        return true;
    }

    // Constructors
    public User() {
        this.subscribedTopics = new HashSet<>();
    }

    // Builder pattern methods
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder id(Integer id) {
            user.setId(id);
            return this;
        }

        public UserBuilder email(String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder username(String username) {
            user.setUsername(username);
            return this;
        }

        public UserBuilder password(String password) {
            user.setPassword(password);
            return this;
        }

        public UserBuilder createdAt(LocalDateTime createdAt) {
            user.setCreatedAt(createdAt);
            return this;
        }

        public UserBuilder subscribedTopics(Set<Topic> subscribedTopics) {
            user.setSubscribedTopics(subscribedTopics);
            return this;
        }

        public User build() {
            return user;
        }
    }
}