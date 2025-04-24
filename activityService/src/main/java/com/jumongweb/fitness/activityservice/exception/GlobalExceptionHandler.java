package com.jumongweb.fitness.activityservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ActivityException.class)
    public ResponseEntity<Map<String, Object>> handleActivityException(ActivityException exception, HttpServletRequest httpServletRequest){
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", exception.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        response.put("path", httpServletRequest.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception, HttpServletRequest httpServletRequest) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", exception.getMessage());
        response.put("timestamp", Instant.now().toString());
        response.put("path", httpServletRequest.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ActivityNotFound.class)
    public ResponseEntity<Map<String, Object>> handleActivityNotFound(
            ActivityNotFound exception,
            HttpServletRequest request
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", exception.getMessage());
        response.put("timestamp", Instant.now().toString());
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", exception.getMessage());
        response.put("timestamp", Instant.now().toString());
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
