package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.TopicRequest;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private final TopicMapper topicMapper;

    /**
     * Constructs a TopicService with necessary dependencies.
     *
     * @param topicRepository repository for Topic entities
     * @param topicMapper     mapper for Topic/TopicDto conversion
     */
    @Autowired
    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Retrieves all topics ordered by creation date descending.
     *
     * @return list of all topics as DTOs
     */

    public List<TopicRequest> getAllTopics() {
        List<Topic> topics = topicRepository.findAllByOrderByCreatedAtDesc();
        return topicMapper.toDtoList(topics);
    }
}