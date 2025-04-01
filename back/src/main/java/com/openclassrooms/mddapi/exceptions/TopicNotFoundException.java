package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when a requested topic is not found in the system.
 * This exception is typically used when attempting to retrieve, update, or
 * delete
 * a topic that doesn't exist in the database.
 */
public class TopicNotFoundException extends RuntimeException {

    /**
     * Constructs a new TopicNotFoundException with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public TopicNotFoundException(String message) {
        super(message);
    }
}