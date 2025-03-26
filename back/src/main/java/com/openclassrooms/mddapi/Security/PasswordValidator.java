package com.openclassrooms.mddapi.Security;

public class PasswordValidator {

    /**
     * Validates that the password meets security requirements:
     * - At least 8 characters
     * - At least one digit
     * - At least one lowercase letter
     * - At least one uppercase letter
     * - At least one special character
     * 
     * @param password The password to validate
     * @return Error message if invalid, null if valid
     */
    public static String validate(String password) {
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