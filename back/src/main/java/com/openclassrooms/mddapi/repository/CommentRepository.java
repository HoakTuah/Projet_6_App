package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Comment entity operations.
 * Provides methods to perform CRUD operations and custom queries on comments.
 * Extends JpaRepository to inherit standard data access methods.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * Retrieves all comments for a specific post, ordered by comment date in
     * descending order.
     * This method is useful for displaying comments on a post with the most recent
     * comments appearing first.
     *
     * @param postId The ID of the post to retrieve comments for
     * @return List of comments for the specified post, ordered by comment date
     *         descending
     */
    List<Comment> findByPostIdOrderByCommentedAtDesc(Integer postId);
}