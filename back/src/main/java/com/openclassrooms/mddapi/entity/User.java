package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entity representing a user in the system.
 * This class is mapped to the 'users' table in the database and stores
 * essential user information for authentication and identification.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Unique identifier for the user, automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * User's email address. Must be unique in the system.
     * Used for account recovery and notifications.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * User's username. Must be unique in the system.
     * Used for authentication and display purposes.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * User's password.
     * Note: This stores the encrypted password, not the plain text.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Timestamp when the user account was created.
     * Automatically set during registration.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}