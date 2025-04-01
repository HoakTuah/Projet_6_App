package com.openclassrooms.mddapi.dto.request;

import lombok.Data;

/**
 * Data Transfer Object for user login requests.
 * Contains the credentials needed for user authentication.
 * 
 * Features:
 * <ul>
 * <li>Flexible login with username or email</li>
 * <li>Secure password handling</li>
 * <li>Lombok @Data for automatic getter/setter generation</li>
 * </ul>
 * 
 * Usage:
 * <ul>
 * <li>Used in authentication endpoints</li>
 * <li>Validates user credentials</li>
 * <li>Generates JWT token upon successful validation</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}