package com.openclassrooms.mddapi.dto.request;

import lombok.Data;

/**
 * Data Transfer Object for user profile update requests.
 * Contains the fields that can be updated in a user profile.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class UpdateProfileRequest {

    /**
     * The new email address for the user.
     * If null or empty, the email will not be updated.
     */
    private String email;

    /**
     * The new username for the user.
     * If null or empty, the username will not be updated.
     */
    private String username;

    /**
     * The new password for the user.
     * If null or empty, the password will not be updated.
     */
    private String password;
}