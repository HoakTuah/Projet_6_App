package com.openclassrooms.mddapi.dto.common;

import com.openclassrooms.mddapi.entity.Topic;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Object that encapsulates user information for data exchange.
 * Contains the basic user details and their topic subscriptions.
 * Used across the application.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */

public class UserDto {

    private Integer id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private Set<Topic> subscribedTopics;

    // ===============================================
    // Getters and Setters
    // ===============================================

    /**
     * Retrieves the user's unique system identifier.
     * 
     * @return The numerical ID that uniquely identifies this user
     */

    public Integer getId() {
        return id;
    }

    /**
     * Sets the user's unique system identifier.
     * 
     * @param id The numerical ID to be assigned to this user
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the user's username.
     * 
     * @return The unique username associated with this account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the user's name in the system.
     * 
     * @param username The new username to be assigned to this account
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the user's email address.
     * 
     * @return The email address associated with this account
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the user's email address in the system.
     * 
     * @param email The new email address to be assigned to this account
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the timestamp of when this user account was created.
     * 
     * @return The date and time when the user was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the account creation timestamp.
     * 
     * @param createdAt The date and time when the user was created to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the set of topics that the user has subscribed to.
     * 
     * @return the set of topics that the user has subscribed to
     */
    public Set<Topic> getSubscribedTopics() {
        return subscribedTopics;
    }

    /**
     * Updates the user's topic subscriptions.
     * 
     * @param subscribedTopics The new set of topics to which the user will be
     *                         subscribed
     */

    public void setSubscribedTopics(Set<Topic> subscribedTopics) {
        this.subscribedTopics = subscribedTopics;
    }
}