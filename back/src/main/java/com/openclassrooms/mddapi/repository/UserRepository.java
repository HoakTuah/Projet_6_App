package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * Provides methods to perform CRUD operations and custom queries on users.
 * Extends JpaRepository to inherit standard data access methods.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their username.
     * 
     * @param username The username to search for
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email address.
     * 
     * @param email The email address to search for
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user with the given username exists.
     * 
     * @param username The username to check
     * @return true if a user with the username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user with the given email exists.
     * 
     * @param email The email to check
     * @return true if a user with the email exists, false otherwise
     */
    boolean existsByEmail(String email);
}