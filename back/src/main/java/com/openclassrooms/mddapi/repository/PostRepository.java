package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Post entity operations.
 * Provides methods to perform CRUD operations and custom queries on posts.
 * Extends JpaRepository to inherit standard data access methods.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    /**
     * Retrieves all posts ordered by publication date in descending order (newest
     * first).
     * This method is useful for displaying posts in chronological order with the
     * most recent posts appearing first.
     * 
     * @return List of posts ordered by publication date in descending order
     */
    List<Post> findAllByOrderByPublishedAtDesc();
}