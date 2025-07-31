package com.example.stocktracker.client.DTO;

public class StockPriceDTO {
    private String symbol;
    private Double price;
    private Double change;
    private String changePercent;
    private String lastUpdated;

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }
    public void setPrice(Double price){
        this.price = price;
    }
    public void setChange(Double change){
        this.change = change;
    }
    public void setChangePercent(String changePercent){
        this.changePercent = changePercent;
    }
    public void setLastUpdated(String lastUpdated){
        this.lastUpdated = lastUpdated;
    }
    public String getSymbol(){
        return this.symbol;
    }
    public Double getPrice(){
        return this.price;
    }
    public Double getChange(){
        return this.change;
    }
    public String getChangePercent(){
        return this.changePercent;
    }
    public String getLastUpdated(){
        return this.lastUpdated;
    }
}
