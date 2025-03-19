package com.openclassrooms.mddapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for registration operation responses.
 * Contains information about the registration result and user details
 * if the registration was successful.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    /**
     * The unique identifier of the newly registered user.
     */
    private Integer userId;

    /**
     * The username of the newly registered user.
     */
    private String username;

    /**
     * The email address of the newly registered user.
     */
    private String email;

    /**
     * The JWT token for the newly registered user.
     */
    private String token;

    /**
     * A message describing the result of the registration attempt.
     */
    private String message;

    /**
     * Indicates whether the registration was successful.
     * True if the user was registered, false otherwise.
     */
    private boolean success;
}