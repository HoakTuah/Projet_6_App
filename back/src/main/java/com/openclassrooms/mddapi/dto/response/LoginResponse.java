package com.openclassrooms.mddapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for login operation responses.
 * Contains information about the authentication result and user details
 * if the login was successful.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
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
     * A message describing the result of the authentication attempt.
     * Examples: "Login successful", "Invalid password", "User not found"
     */
    private String message;

    /**
     * Indicates whether the authentication was successful.
     * True if the user was authenticated, false otherwise.
     */
    private boolean success;
}