package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.PostRequest;
import com.openclassrooms.mddapi.dto.common.PostDto;
import com.openclassrooms.mddapi.entity.Post;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PostMapper postMapper;

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
     *
     * @param request The post creation request
     * @return The created post as a DTO
     * @throws EntityNotFoundException if the user or topic is not found
     */
    @Transactional
    public PostDto createPost(PostRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new EntityNotFoundException("Topic not found"));

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
     * Retrieves all posts from the system, ordered by publication date.
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
     * @throws EntityNotFoundException if the post is not found
     */
    public PostDto getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
        return postMapper.toDto(post);
    }
}