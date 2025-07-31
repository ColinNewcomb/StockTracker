package com.example.stocktracker.client.DTO;

public class CompanyInfoDTO {
    private String symbol;
    private String name;
    private String exchange;
    private String currency;
    private String country;
    private String sector;
    private String industry;

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setExchange(String exchange){
        this.exchange = exchange;
    }
    public void setCurrency(String currency){
        this.currency = currency;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public void setSector(String sector){
        this.sector = sector;
    }
    public void setIndustry(String industry){
        this.industry = industry;
    }
    public String getSymbol(){
        return this.symbol;
    }
    public String getName(){
        return this.name;
    }
    public String getExchange(){
        return this.exchange;
    }
    public String getCurrency(){
        return this.currency;
    }
    public String getCountry(){
        return this.country;
    }
    public String getSector(){
        return this.sector;
    }
    public String getIndustry(){
        return this.industry;
    }
}
