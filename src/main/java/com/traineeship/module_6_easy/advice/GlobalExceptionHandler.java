package com.traineeship.module_6_easy.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public Map<String, Object> handleBookNotFoundException(BookNotFoundException e) {
        return Map.of(
                "status", HttpStatus.NOT_FOUND.value(),
                "message", e.getMessage()
        );
    }
}
