package com.openclassrooms.mddapi.dto.request;

import lombok.Data;

/**
 * Data Transfer Object for user registration requests.
 * Contains the information needed to create a new user account.
 * 
 * Features:
 * <ul>
 * <li>Email validation and uniqueness check</li>
 * <li>Username validation and uniqueness check</li>
 * <li>Password strength requirements</li>
 * <li>Automatic account initialization</li>
 * </ul>
 * 
 * Usage:
 * <ul>
 * <li>Used in user registration endpoints</li>
 * <li>Validates new user data before account creation</li>
 * <li>Ensures data integrity and security</li>
 * </ul>
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