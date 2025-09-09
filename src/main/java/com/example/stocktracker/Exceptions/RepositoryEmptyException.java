package com.example.stocktracker.Exceptions;

public class RepositoryEmptyException extends RuntimeException {
    public RepositoryEmptyException() {
        super("The repository is empty.");
    }
}
