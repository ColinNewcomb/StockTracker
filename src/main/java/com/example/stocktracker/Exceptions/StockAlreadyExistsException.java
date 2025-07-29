package com.example.stocktracker.Exceptions;

public class StockAlreadyExistsException extends RuntimeException{
    public StockAlreadyExistsException(String symbol){
        super("WatchList Already Contains " + symbol);
    }
}
