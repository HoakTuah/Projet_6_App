package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when attempting to create a user that already exists in the
 * system.
 * This exception is typically used during user registration when the email or
 * username
 * is already associated with an existing account.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail
     * message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}