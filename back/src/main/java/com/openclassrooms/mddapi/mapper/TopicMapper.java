package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicRequest;
import com.openclassrooms.mddapi.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * Mapper for the Topic entity and its DTO.
 * Uses MapStruct to automatically generate the implementation.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    /**
     * Convert a Topic entity to a TopicDto.
     *
     * @param topic the topic entity to convert
     * @return the converted TopicDto
     */
    TopicRequest toDto(Topic topic);

    /**
     * Convert a TopicDto to a Topic entity.
     *
     * @param topicDto the topic DTO to convert
     * @return the converted Topic entity
     */
    Topic toEntity(TopicRequest topicRequest);

    /**
     * Convert a list of Topic entities to a list of TopicDtos.
     *
     * @param topics the list of topic entities to convert
     * @return the list of converted TopicDtos
     */
    List<TopicRequest> toDtoList(List<Topic> topics);
}