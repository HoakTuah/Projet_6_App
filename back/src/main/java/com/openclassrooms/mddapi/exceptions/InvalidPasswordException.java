package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when a user provides an invalid password during
 * authentication
 * or password change operations. This exception is typically used when the
 * password
 * doesn't meet security requirements or doesn't match the stored password.
 */
public class InvalidPasswordException extends RuntimeException {

    /**
     * Constructs a new InvalidPasswordException with the specified detail message.
     *
     * @param message the detail message describing the reason for the invalid
     *                password
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}