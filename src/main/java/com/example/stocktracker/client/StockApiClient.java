package com.example.stocktracker.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.stocktracker.client.DTO.CompanyInfoDTO;
import com.example.stocktracker.client.DTO.StockPriceDTO;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class StockApiClient {
    @Value("${alphavantage.api.key}")
    private String apiKey;
    
    private final WebClient webClient;
    
    public StockApiClient() {
        this.webClient = WebClient.builder()
            .baseUrl("https://www.alphavantage.co/query")
            .build();
    }
    
    public CompanyInfoDTO getCompanyInfo(String symbol) {
        try {
            JsonNode response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("function", "OVERVIEW")
                    .queryParam("symbol", symbol)
                    .queryParam("apikey", apiKey)
                    .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
            
            // Parse the JSON response
            CompanyInfoDTO dto = new CompanyInfoDTO();
            dto.setSymbol(response.get("Symbol").asText());
            dto.setName(response.get("Name").asText());
            dto.setExchange(response.get("Exchange").asText());
            dto.setCurrency(response.get("Currency").asText());
            dto.setCountry(response.get("Country").asText());
            dto.setSector(response.get("Sector").asText());
            dto.setIndustry(response.get("Industry").asText());
            
            return dto;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch company info for " + symbol, e);
        }
    }
    
    public StockPriceDTO getCurrentPrice(String symbol) {
        try {
            JsonNode response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("function", "GLOBAL_QUOTE")
                    .queryParam("symbol", symbol)
                    .queryParam("apikey", apiKey)
                    .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
            
            // Alpha Vantage returns nested JSON
            JsonNode quote = response.get("Global Quote");
            
            StockPriceDTO dto = new StockPriceDTO();
            dto.setSymbol(quote.get("01. symbol").asText());
            dto.setPrice(quote.get("05. price").asDouble());
            dto.setChange(quote.get("09. change").asDouble());
            dto.setChangePercent(quote.get("10. change percent").asText());
            dto.setLastUpdated(quote.get("07. latest trading day").asText());
            
            return dto;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch price for " + symbol, e);
        }
    }
}
