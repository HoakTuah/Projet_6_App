package com.openclassrooms.mddapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * This class provides centralized exception handling across all @RequestMapping
 * methods
 * through @ExceptionHandler methods.
 * 
 * It handles various custom exceptions and returns appropriate HTTP status
 * codes
 * along with structured error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserNotFoundException.
     * Returns a 404 NOT_FOUND status with error details.
     *
     * @param e the UserNotFoundException that was thrown
     * @return ResponseEntity containing error details and HTTP status
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", e.getMessage());
        response.put("error", "L'utilisateur n'existe pas");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UsernameNotFoundException from Spring Security.
     * Returns a 404 NOT_FOUND status with error details.
     *
     * @param e the UsernameNotFoundException that was thrown
     * @return ResponseEntity containing error details and HTTP status
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", e.getMessage());
        response.put("error", "L'utilisateur n'existe pas");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UserAlreadyExistsException.
     * Returns a 409 CONFLICT status with error details.
     *
     * @param e the UserAlreadyExistsException that was thrown
     * @return ResponseEntity containing error details and HTTP status
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("message", e.getMessage());
        response.put("error", "L'utilisateur existe déjà");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handles InvalidPasswordException.
     * Returns a 400 BAD_REQUEST status with error details.
     *
     * @param e the InvalidPasswordException that was thrown
     * @return ResponseEntity containing error details and HTTP status
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPasswordException(InvalidPasswordException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", e.getMessage());
        response.put("error", "Mot de passe invalide");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles TopicNotFoundException.
     * Returns a 404 NOT_FOUND status with error details.
     *
     * @param e the TopicNotFoundException that was thrown
     * @return ResponseEntity containing error details and HTTP status
     */
    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTopicNotFoundException(TopicNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", e.getMessage());
        response.put("error", "Le thème n'existe pas");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles TopicSubscriptionException.
     * Returns a 400 BAD_REQUEST status with error details.
     *
     * @param e the TopicSubscriptionException that was thrown
     * @return ResponseEntity containing error details and HTTP status
     */
    @ExceptionHandler(TopicSubscriptionException.class)
    public ResponseEntity<Map<String, Object>> handleTopicSubscriptionException(TopicSubscriptionException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", e.getMessage());
        response.put("error", "Erreur d'abonnement au thème");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles PostNotFoundException.
     * Returns a 404 NOT_FOUND status with error details.
     *
     * @param e the PostNotFoundException that was thrown
     * @return ResponseEntity containing error details and HTTP status
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePostNotFoundException(PostNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", e.getMessage());
        response.put("error", "l'article n'existe pas");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation exceptions for @Valid annotated request parameters and
     * body.
     * Returns a 400 BAD_REQUEST status with detailed validation errors.
     *
     * @param e the MethodArgumentNotValidException that was thrown
     * @return ResponseEntity containing validation errors and HTTP status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new LinkedHashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);
        response.put("error", "Validation échouée");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all unhandled exceptions.
     * Returns a 500 INTERNAL_SERVER_ERROR status with error details.
     *
     * @param e the Exception that was thrown
     * @return ResponseEntity containing error details and HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "Une erreur inattendue s'est produite");
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}