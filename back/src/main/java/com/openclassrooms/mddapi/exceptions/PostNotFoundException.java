package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when a requested post is not found in the system.
 * This exception is typically used when attempting to retrieve, update, or
 * delete
 * a post that doesn't exist in the database.
 */
public class PostNotFoundException extends RuntimeException {

    /**
     * Constructs a new PostNotFoundException with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public PostNotFoundException(String message) {
        super(message);
    }
}