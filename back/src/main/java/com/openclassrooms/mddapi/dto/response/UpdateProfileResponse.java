package com.openclassrooms.mddapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for profile update operation responses.
 * Contains information about the update result and user details.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class UpdateProfileResponse {

    /**
     * The unique identifier of the user.
     */
    private Integer userId;

    /**
     * The username of the user after the update.
     */
    private String username;

    /**
     * The email address of the user after the update.
     */
    private String email;

    /**
     * The JWT token for the user after the update.
     * Only provided when email is changed.
     */
    private String token;

    /**
     * A message describing the result of the update attempt.
     * Examples: "Profile updated successfully", "Email already in use"
     */
    private String message;

    /**
     * Indicates whether the update was successful.
     * True if the profile was updated, false otherwise.
     */
    private boolean success;
}