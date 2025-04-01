package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.response.TopicResponse;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.exceptions.TopicNotFoundException;
import com.openclassrooms.mddapi.exceptions.TopicSubscriptionException;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.List;

/**
 * Service class for managing Topic entities and their subscriptions.
 * Provides business logic for topic operations including retrieval,
 * subscription management,
 * and user-topic relationship handling.
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

    /**
     * Constructs a TopicService with required dependencies.
     *
     * @param topicRepository Repository for topic data operations
     * @param userRepository  Repository for user data operations
     * @param topicMapper     Mapper for DTO conversions
     */
    @Autowired
    public TopicService(TopicRepository topicRepository,
            UserRepository userRepository,
            TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Retrieves all topics ordered by creation date in descending order.
     *
     * @return List of TopicResponse objects representing all topics
     */
    public List<TopicResponse> getAllTopics() {
        List<Topic> topics = topicRepository.findAllByOrderByCreatedAtDesc();
        return topics.stream()
                .map(topic -> topicMapper.toResponse(topic))
                .toList();
    }

    /**
     * Subscribes the current user to a specific topic.
     * Handles validation to prevent duplicate subscriptions.
     *
     * @param topicId The ID of the topic to subscribe to
     * @return TopicResponse containing the updated topic information
     * @throws UsernameNotFoundException  if the current user is not found
     * @throws TopicNotFoundException     if the topic is not found
     * @throws TopicSubscriptionException if the user is already subscribed
     */
    @Transactional
    public TopicResponse subscribeTopic(Integer topicId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("thème non trouvé avec l'ID : " + topicId));

        if (topic.hasSubscription(user)) {
            throw new TopicSubscriptionException("Vous êtes déjà abonné à ce thème");
        }

        // Add subscription
        topic.addSubscription(user);
        topicRepository.save(topic);

        // Force refresh from database to get updated subscriber count
        topic = topicRepository.findById(topicId).get();

        return topicMapper.toResponse(topic, "Abonnement au thème réussi", true);
    }

    /**
     * Unsubscribes the current user from a specific topic.
     * Handles validation to ensure the user is subscribed before unsubscribing.
     *
     * @param topicId The ID of the topic to unsubscribe from
     * @return TopicResponse containing the updated topic information
     * @throws UsernameNotFoundException  if the current user is not found
     * @throws TopicNotFoundException     if the topic is not found
     * @throws TopicSubscriptionException if the user is not subscribed
     */
    @Transactional
    public TopicResponse unsubscribeTopic(Integer topicId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("thème non trouvé avec l'ID : " + topicId));

        if (!topic.hasSubscription(user)) {
            throw new TopicSubscriptionException("Vous n'êtes pas abonné à ce thème");
        }

        // Remove subscription
        topic.removeSubscription(user);
        topicRepository.save(topic);

        // Force refresh from database to get updated subscriber count
        topic = topicRepository.findById(topicId).get();

        return topicMapper.toResponse(topic, "Désabonnement du thème réussi", true);
    }

    /**
     * Retrieves all topics subscribed by the current user.
     *
     * @return List of TopicResponse objects representing subscribed topics
     * @throws UsernameNotFoundException if the current user is not found
     */
    public List<TopicResponse> getSubscribedTopics() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas"));

        Set<Topic> subscribedTopics = user.getSubscribedTopics();

        return subscribedTopics.stream()
                .map(topic -> topicMapper.toResponse(topic, "Thème trouvé", true))
                .collect(Collectors.toList());
    }
}