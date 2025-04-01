package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when a requested user is not found in the system.
 * This exception is typically used when attempting to retrieve or manipulate
 * a user that doesn't exist in the database.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}