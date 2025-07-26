package com.example.stocktracker.DTO;

import jakarta.validation.constraints.NotBlank;

public class WatchedStockRequestDTO {

    @NotBlank
    private String symbol;

    @NotBlank
    private String name;

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

}