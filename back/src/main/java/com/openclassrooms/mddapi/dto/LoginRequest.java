package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * Data Transfer Object for user login requests.
 * Contains the credentials needed for user authentication.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class LoginRequest {

    /**
     * The username or email of the user attempting to log in.
     * The system will determine whether this is a username or email.
     */
    private String username;

    /**
     * The password provided by the user for authentication.
     * Will be compared with the stored password for validation.
     */
    private String password;
}