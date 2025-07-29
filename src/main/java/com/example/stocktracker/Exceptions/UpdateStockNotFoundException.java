package com.example.stocktracker.Exceptions;

public class UpdateStockNotFoundException extends RuntimeException{
    public UpdateStockNotFoundException(String symbol){
        super("Update Failed " + symbol + " Was Not Found.");
    }
}
