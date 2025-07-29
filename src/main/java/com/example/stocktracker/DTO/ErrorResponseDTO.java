package com.example.stocktracker.DTO;

import java.time.LocalDateTime;

public class ErrorResponseDTO {
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private int status;

    // Constructors
    public ErrorResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponseDTO(String error, String message, String path, int status) {
        this();
        this.error = error;
        this.message = message;
        this.path = path;
        this.status = status;
    }

    // Getters and setters
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
