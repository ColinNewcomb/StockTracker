package com.example.stocktracker.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class WatchedStockRequestDTO {

    @NotBlank(message = "symbol cannot be blank")
    @Size(max = 10, message = "Message cannot be too long")
    private String symbol;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    @Positive(message = "Price must be Positive")
    private Double price;

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
}