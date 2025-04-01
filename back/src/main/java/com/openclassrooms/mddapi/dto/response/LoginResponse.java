package com.openclassrooms.mddapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

/**
 * Data Transfer Object for login operation responses.
 * Contains information about the authentication result and user details
 * if the login was successful.
 * 
 * Features:
 * <ul>
 * <li>Complete user authentication information</li>
 * <li>JWT token generation</li>
 * <li>User preferences and subscriptions</li>
 * <li>Detailed feedback messages</li>
 * </ul>
 * 
 * Usage:
 * <ul>
 * <li>Used in authentication endpoints</li>
 * <li>Provides session initialization data</li>
 * <li>Enables user preference loading</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    /**
     * The unique identifier of the authenticated user.
     */
    private Integer userId;

    /**
     * The username of the authenticated user.
     */
    private String username;

    /**
     * The email address of the authenticated user.
     */
    private String email;

    /**
     * The JWT token for the authenticated user.
     */
    private String token;

    /**
     * A message describing the result of the authentication attempt.
     * Examples: "Login successful", "Invalid password", "User not found"
     */
    private String message;

    /**
     * Indicates whether the authentication was successful.
     * True if the user was authenticated, false otherwise.
     */
    private boolean success;

    /**
     * The list of subscribed topics for the authenticated user.
     */
    private Set<String> subscribedTopics;
}