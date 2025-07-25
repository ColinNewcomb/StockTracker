package com.example.stocktracker.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> HandleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Stock Not Found: " + e.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> HandleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Conflict: " + e.getMessage());
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> HandleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("A null pointer error occurred: " + e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> HandleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("A runtime error occurred: " + e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> HandleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + e.getMessage());
    }
}  
