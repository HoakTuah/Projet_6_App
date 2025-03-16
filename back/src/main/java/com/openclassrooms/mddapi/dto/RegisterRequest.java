package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * Data Transfer Object for user registration requests.
 * Contains the information needed to create a new user account.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class RegisterRequest {

    /**
     * The email address for the new user account.
     * Must be unique in the system.
     */
    private String email;

    /**
     * The username for the new user account.
     */
    private String username;

    /**
     * The password for the new user account.
     */
    private String password;
}