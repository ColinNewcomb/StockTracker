package com.example.stocktracker.controller;
import com.example.stocktracker.DTO.WatchedStockRequestDTO;
import com.example.stocktracker.DTO.WatchedStockResponseDTO;
import com.example.stocktracker.model.WatchedStock;
import com.example.stocktracker.service.StockService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/watchlist")
public class StockController {

    private final StockService stockService;

    // Constructor injection for the repository
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Endpoint to get all watched stocks
    @GetMapping
    public List<WatchedStockResponseDTO> getAll() {
        return stockService.getAllStocks();
    }

    // Endpoint to get a stock by its symbol
    @GetMapping("/{symbol}")
    public WatchedStockResponseDTO getStockBySymbol(@PathVariable String symbol) {
        return stockService.getStockBySymbol(symbol);
    }

    // Endpoints to add Stocks to Rep
    @PostMapping
    public WatchedStockResponseDTO addStock(@Valid @RequestBody WatchedStockRequestDTO dto) {
        return stockService.addstock(dto);
    }

    // Endpoint to update a stock by its symbol
    @PutMapping("/{symbol}")
    public WatchedStockResponseDTO updateStock(@PathVariable String symbol, @Valid @RequestBody WatchedStock stock) {
        return stockService.updateStock(symbol, stock);
    }

    //Endpoint to query stock by its name
    @GetMapping("/search")
    public WatchedStockResponseDTO searchByName(@RequestParam String query) {
        return stockService.searchByName(query);
        
    }

    // Endpoint to delete a stock by its symbol
    @DeleteMapping("/{symbol}")
    public String deleteStock(@PathVariable String symbol) {
        return stockService.deleteStock(symbol);
    }

    // Endpoint to delete all stocks from the watchlist
    @DeleteMapping
    public ResponseEntity<String> deleteAllStocks() {
        return ResponseEntity.ok(stockService.deleteAllStocks());
    }
}
