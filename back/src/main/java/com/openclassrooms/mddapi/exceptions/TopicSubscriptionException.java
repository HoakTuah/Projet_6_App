package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when there is an error related to topic subscriptions.
 * This exception is typically used when attempting to subscribe or unsubscribe
 * from a topic encounters an error, such as already being subscribed or
 * attempting to unsubscribe from a topic one isn't subscribed to.
 */
public class TopicSubscriptionException extends RuntimeException {

    /**
     * Constructs a new TopicSubscriptionException with the specified detail
     * message.
     *
     * @param message the detail message describing the reason for the subscription
     *                error
     */
    public TopicSubscriptionException(String message) {
        super(message);
    }
}