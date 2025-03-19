package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.response.TopicResponse;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.List;

/**
 * Service class for managing Topic entities.
 * Provides business logic for topic operations.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final TopicMapper topicMapper;

    @Autowired
    public TopicService(TopicRepository topicRepository,
            UserRepository userRepository,
            TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.topicMapper = topicMapper;
    }

    public List<TopicResponse> getAllTopics() {
        List<Topic> topics = topicRepository.findAllByOrderByCreatedAtDesc();
        return topics.stream()
                .map(topic -> topicMapper.toResponse(topic))
                .toList();
    }

    @Transactional
    public TopicResponse subscribeTopic(Integer topicId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        if (topic.hasSubscription(user)) {
            return topicMapper.toResponse(topic, "Already subscribed to this topic", false);
        }

        // Add subscription
        topic.addSubscription(user);
        topicRepository.save(topic);

        // Force refresh from database to get updated subscriber count
        topic = topicRepository.findById(topicId).get();

        return topicMapper.toResponse(topic, "Successfully subscribed to topic", true);
    }

    @Transactional
    public TopicResponse unsubscribeTopic(Integer topicId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        if (!topic.hasSubscription(user)) {
            return topicMapper.toResponse(topic, "Not subscribed to this topic", false);
        }

        // Remove subscription
        topic.removeSubscription(user);
        topicRepository.save(topic);

        // Force refresh from database to get updated subscriber count
        topic = topicRepository.findById(topicId).get();

        return topicMapper.toResponse(topic, "Successfully unsubscribed from topic", true);
    }

    public List<TopicResponse> getSubscribedTopics() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Add debug logging
        System.out.println("Getting subscribed topics for user: " + email);

        Set<Topic> subscribedTopics = user.getSubscribedTopics();
        System.out.println("Found " + subscribedTopics.size() + " subscribed topics");

        return subscribedTopics.stream()
                .map(topic -> topicMapper.toResponse(topic, "Topic found", true))
                .collect(Collectors.toList());
    }
}