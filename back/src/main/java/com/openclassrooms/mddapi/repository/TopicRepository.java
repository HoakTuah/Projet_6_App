package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Topic entity.
 * Provides methods to interact with the topics table in the database.
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
     *
     * @return list of topics ordered by creation date descending
     */
    List<Topic> findAllByOrderByCreatedAtDesc();

    /**
     * Retrieves all topics subscribed to by a specific user.
     *
     * @param user the user to check subscriptions for
     * @return list of topics subscribed to by the user
     */
    List<Topic> findBySubscribersContaining(User user);
}