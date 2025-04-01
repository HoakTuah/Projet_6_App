package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Topic entity operations.
 * Provides methods to perform CRUD operations and custom queries on topics.
 * Extends JpaRepository to inherit standard data access methods.
 * 
 * This repository handles:
 * <ul>
 * <li>Topic creation and management</li>
 * <li>Topic subscription tracking</li>
 * <li>Topic listing and sorting</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    /**
     * Retrieves all topics ordered by creation date in descending order (newest
     * first).
     * This method is useful for displaying topics in chronological order with the
     * most recent
     * topics appearing first.
     *
     * @return List of topics ordered by creation date in descending order
     */
    List<Topic> findAllByOrderByCreatedAtDesc();

    /**
     * Retrieves all topics that a specific user has subscribed to.
     * This method is useful for displaying a user's subscribed topics or managing
     * topic subscriptions.
     *
     * @param user The user to check subscriptions for
     * @return List of topics that the user has subscribed to
     */
    List<Topic> findBySubscribersContaining(User user);
}