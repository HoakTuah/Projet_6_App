package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.common.CommentDto;
import com.openclassrooms.mddapi.dto.request.CommentRequest;
import com.openclassrooms.mddapi.entity.Comment;
import com.openclassrooms.mddapi.entity.Post;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class that handles comment-related operations.
 * Provides functionality for creating comments and retrieving comments for
 * posts.
 * Manages the relationship between users, posts, and comments.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    /**
     * Constructs a CommentService with required dependencies.
     * 
     * @param commentRepository Repository for comment data access operations
     * @param postRepository    Repository for post data access operations
     * @param userRepository    Repository for user data access operations
     * @param commentMapper     Mapper for converting between Comment entities and
     *                          DTOs
     */

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository,
            CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Creates a new comment for a post.
     * Uses the currently authenticated user as the author of the comment.
     * Sets the creation timestamp and associates the comment with the appropriate
     * post.
     * 
     * @param request The comment request containing post ID and content
     * @return The created comment as a DTO
     * @throws RuntimeException        If the authenticated user cannot be found
     * @throws EntityNotFoundException If the referenced post does not exist
     */

    @Transactional
    public CommentDto createComment(CommentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(request.getContent());
        comment.setCommentedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    /**
     * Retrieves all comments for a specific post, ordered by creation time
     * (descending).
     * Returns the most recent comments first.
     * 
     * @param postId The ID of the post to retrieve comments
     * @return A list of comments for the post as DTOs
     */

    public List<CommentDto> getPostComments(Integer postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCommentedAtDesc(postId);
        return commentMapper.toDtoList(comments);
    }
}