package com.openclassrooms.mddapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a topic in the system.
 * A topic is a subject area that users can subscribe to and post about.
 * Manages user subscriptions through a many-to-many relationship.
 * 
 * Features:
 * <ul>
 * <li>Topic information storage</li>
 * <li>User subscription management</li>
 * <li>Bidirectional relationship with User entity</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "subscribers") // Exclude from ToString
public class Topic {

    /**
     * Unique identifier for the topic.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Title of the topic.
     */
    private String title;

    /**
     * Description or content of the topic.
     */
    private String content;

    /**
     * Timestamp when the topic was created.
     */
    private LocalDateTime createdAt;

    /**
     * Set of users who have subscribed to this topic.
     * Uses a many-to-many relationship with the User entity.
     * The relationship is managed through a join table named 'subscriptions'.
     */
    @ManyToMany
    @JoinTable(name = "subscriptions", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> subscribers = new HashSet<>();

    /**
     * Custom equals implementation to prevent infinite recursion.
     * Compares topics based on their ID.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Topic))
            return false;
        Topic topic = (Topic) o;
        return id != null && id.equals(topic.getId());
    }

    /**
     * Custom hashCode implementation to prevent infinite recursion.
     * Uses the class type for hashing.
     *
     * @return The hash code of the object
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Adds a user to the topic's subscribers.
     * Maintains bidirectional relationship between Topic and User.
     *
     * @param user The user to add as a subscriber
     */
    public void addSubscription(User user) {
        subscribers.add(user);
        user.getSubscribedTopics().add(this);
    }

    /**
     * Removes a user from the topic's subscribers.
     * Maintains bidirectional relationship between Topic and User.
     *
     * @param user The user to remove from subscribers
     */
    public void removeSubscription(User user) {
        subscribers.remove(user);
        user.getSubscribedTopics().remove(this);
    }

    /**
     * Checks if a user is subscribed to this topic.
     *
     * @param user The user to check
     * @return true if the user is subscribed, false otherwise
     */
    public boolean hasSubscription(User user) {
        return subscribers.contains(user);
    }
}