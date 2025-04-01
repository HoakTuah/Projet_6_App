package com.openclassrooms.mddapi.mapper;

/**
 * Generic mapper interface for converting between DTOs (Data Transfer Objects)
 * and entities.
 * This interface provides a standard contract for mapping operations across the
 * application.
 *
 * @param <D> The type of the DTO
 * @param <E> The type of the entity
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
public interface EntityMapper<D, E> {

    /**
     * Converts a DTO to its corresponding entity.
     *
     * @param dto The DTO to convert
     * @return The corresponding entity
     */
    E toEntity(D dto);

    /**
     * Converts an entity to its corresponding DTO.
     *
     * @param entity The entity to convert
     * @return The corresponding DTO
     */
    D toDto(E entity);
}