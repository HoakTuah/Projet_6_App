package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Topic;
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
     * Checks if a topic with the given title exists.
     *
     * @param title the title to check
     * @return true if a topic with the title exists, false otherwise
     */
    boolean existsByTitle(String title);
}