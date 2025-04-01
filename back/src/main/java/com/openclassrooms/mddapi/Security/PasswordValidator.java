package com.openclassrooms.mddapi.Security;

/**
 * Utility class for password validation in the application.
 * Provides static methods to validate password strength and security
 * requirements.
 * This class ensures that all passwords in the system meet minimum security
 * standards
 * to protect user accounts and sensitive information.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
public class PasswordValidator {

    /**
     * Validates that the password meets security requirements.
     * Performs comprehensive checks to ensure password strength:
     * <ul>
     * <li>Minimum length of 8 characters</li>
     * <li>At least one digit (0-9)</li>
     * <li>At least one lowercase letter (a-z)</li>
     * <li>At least one uppercase letter (A-Z)</li>
     * <li>At least one special character (!@#$%^*()_+-=[]{};':"\|,.>/?)
     * </ul>
     * 
     * @param password The password string to validate
     * @return A descriptive error message if the password is invalid, null if the
     *         password is valid
     * @throws IllegalArgumentException if the password parameter is null
     */
    public static String validate(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        if (password.length() < 8) {
            return "Le mot de passe doit contenir au moins 8 caractères";
        }

        if (!password.matches(".*\\d.*")) {
            return "Le mot de passe doit contenir au moins un chiffre";
        }

        if (!password.matches(".*[a-z].*")) {
            return "Le mot de passe doit contenir au moins une lettre minuscule";
        }

        if (!password.matches(".*[A-Z].*")) {
            return "Le mot de passe doit contenir au moins une lettre majuscule";
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return "Le mot de passe doit contenir au moins un caractère spécial";
        }

        return null; // Password is valid
    }
}