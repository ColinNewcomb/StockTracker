package com.example.stocktracker.Exceptions;

public class EmptyWatchListException extends RuntimeException {
    public EmptyWatchListException(){
        super("WatchList is Empty.");
    }
}
