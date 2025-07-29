package com.example.stocktracker.Exceptions;

public class StockNotFoundException extends RuntimeException{
    public StockNotFoundException(String Symbol){
        super(Symbol + " Not Found in WatchList.");
    }
}
