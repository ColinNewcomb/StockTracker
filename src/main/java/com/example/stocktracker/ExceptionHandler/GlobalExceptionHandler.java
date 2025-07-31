package com.example.stocktracker.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.stocktracker.Exceptions.*;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import jakarta.servlet.http.HttpServletRequest;

import com.example.stocktracker.DTO.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
        "DATABASE_ERROR",
        "Unable to access database. Please try again later.",
        request.getRequestURI(),
        HttpStatus.SERVICE_UNAVAILABLE.value()
    );
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request) {
    ErrorResponseDTO error = new ErrorResponseDTO(
        "DATA_INTEGRITY_VIOLATION",
        "Data constraint violation. This record may already exist.",
        request.getRequestURI(),
        HttpStatus.CONFLICT.value()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
        "MALFORMED_REQUEST",
        "Request body is malformed or contains invalid JSON.",
        request.getRequestURI(),
        HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpServletRequest request) {
    ErrorResponseDTO error = new ErrorResponseDTO(
        "MISSING_PARAMETER",
        "Required parameter '" + e.getParameterName() + "' is missing.",
        request.getRequestURI(),
        HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    //Consider NO_CONTENT As HttpStatus
    @ExceptionHandler(EmptyWatchListException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmptyWatchListException(EmptyWatchListException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "EMPTY_WATCHLIST",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(StockAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleStockAlreadyExists(StockAlreadyExistsException e, HttpServletRequest request){
        ErrorResponseDTO error = new ErrorResponseDTO(
            "STOCK_ALREADY_EXISTS",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleStockNotFoundException(StockNotFoundException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "STOCK_NOT_FOUND",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(UpdateStockNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleLookUpStockNotFoundException(UpdateStockNotFoundException e, HttpServletRequest request){
        ErrorResponseDTO error = new ErrorResponseDTO(
            "STOCK_NOT_FOUND",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    //NOT_FOUND Illegal Argument
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "ILLEGAL_ARGUMENT",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    //CONFLICT Conflict
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalStateException(IllegalStateException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "Conflict",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    //BAD_REQUEST Invalid request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        StringBuilder messageBuilder = new StringBuilder("Validation failed: ");
            e.getBindingResult().getFieldErrors().forEach(error -> {
            messageBuilder.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
    });
    
    ErrorResponseDTO error = new ErrorResponseDTO(
        "VALIDATION_FAILED",
        messageBuilder.toString(),
        request.getRequestURI(),
        HttpStatus.BAD_REQUEST.value()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    //INTERNAL SERVER ERROR A null pointer error occurred
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDTO> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "A null pointer error occurred",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    //INTERNAL SERVER ERROR A runtime error occurred
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "A runtime error occurred",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    //INTERNAL SERVER ERROR An unexpected error occurred
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            "An unexpected error occurred",
            e.getMessage(),
            request.getRequestURI(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}  
