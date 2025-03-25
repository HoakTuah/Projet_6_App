package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.common.PostDto;
import com.openclassrooms.mddapi.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.usernameDisplay", target = "username"),
            @Mapping(source = "topic.id", target = "topicId"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "publishedAt", target = "publishedAt")
    })
    PostDto toDto(Post post);
}