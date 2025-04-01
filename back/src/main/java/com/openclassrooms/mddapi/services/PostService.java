package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.PostRequest;
import com.openclassrooms.mddapi.dto.common.PostDto;
import com.openclassrooms.mddapi.entity.Post;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.exceptions.PostNotFoundException;
import com.openclassrooms.mddapi.exceptions.TopicNotFoundException;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that handles post-related operations in the forum system.
 * Provides comprehensive functionality for creating, retrieving, and managing
 * posts.
 * Manages the relationships between users, topics, and posts while ensuring
 * data integrity
 * and proper authorization.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PostMapper postMapper;

    /**
     * Constructs a PostService with required dependencies.
     * 
     * @param postRepository  Repository for post data access operations
     * @param userRepository  Repository for user data access operations
     * @param topicRepository Repository for topic data access operations
     * @param postMapper      Mapper for converting between Post entities and DTOs
     */
    @Autowired
    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            TopicRepository topicRepository,
            PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.postMapper = postMapper;
    }

    /**
     * Creates a new post in the system.
     * Associates the post with the current user and specified topic.
     * Sets the publication timestamp and validates all required relationships.
     * 
     * @param request The post creation request containing title, content, and topic
     *                ID
     * @return The created post as a DTO
     * @throws UsernameNotFoundException if the authenticated user cannot be found
     * @throws TopicNotFoundException    if the referenced topic does not exist
     */
    @Transactional
    public PostDto createPost(PostRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new TopicNotFoundException("Thème non trouvé"));

        Post post = new Post();
        post.setUser(user);
        post.setTopic(topic);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPublishedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);
        return postMapper.toDto(savedPost);
    }

    /**
     * Retrieves all posts from the system, ordered by publication date
     * (descending).
     * Returns the most recent posts first.
     * 
     * @return List of posts as DTOs
     */
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByPublishedAtDesc();
        return posts.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific post by its ID.
     * 
     * @param id The ID of the post to retrieve
     * @return The post as a DTO
     * @throws PostNotFoundException if the post is not found with the given ID
     */
    public PostDto getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Article non trouvé avec l'ID : " + id));
        return postMapper.toDto(post);
    }
}