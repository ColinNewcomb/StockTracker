package com.example.stocktracker.controller;

import com.example.stocktracker.model.WatchedStock;
import com.example.stocktracker.repository.WatchedStockRep;

import jakarta.validation.Valid;

//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class StockController {

    private final WatchedStockRep repository;

    // Constructor injection for the repository
    public StockController(WatchedStockRep repository) {
        this.repository = repository;
    }

    // Endpoint to get all watched stocks
    @GetMapping
    public List<WatchedStock> getAll() {
        List<WatchedStock> stocks = repository.findAll();
        if (stocks.isEmpty()) {
            throw new IllegalArgumentException("No stocks found in the watchlist.");
        }
        return stocks;
    }

    // Endpoint to get a stock by its symbol
    @GetMapping("/{symbol}")
    public WatchedStock getStockBySymbol(@PathVariable String symbol) {
        return repository.findBySymbolIgnoreCase(symbol).orElseThrow(() -> 
            new IllegalArgumentException("Stock Not Found: " + symbol));
    }

    // Endpoints to add Stocks to Rep
    @PostMapping
    public WatchedStock addStock(@Valid @RequestBody WatchedStock stock) {
        if (repository.existsBySymbolIgnoreCase(stock.getSymbol())) {
            throw new IllegalStateException("Stock already exists: " + stock.getSymbol());
        }
        return repository.save(stock);
    }

    // Endpoint to update a stock by its symbol
    @PutMapping("/{symbol}")
    public WatchedStock updateStock(@Valid @PathVariable String symbol, @RequestBody WatchedStock stock) {
        WatchedStock existingStock = repository.findBySymbolIgnoreCase(symbol)
        .orElseThrow(() -> new IllegalArgumentException("Stock Not Found: " + symbol));
    
        existingStock.setName(stock.getName());
        existingStock.setPrice(stock.getPrice());
        return repository.save(existingStock);
    }

    // Endpoint to delete a stock by its symbol
    @DeleteMapping("/{symbol}")
    public String deleteStock(@PathVariable String symbol) {
        WatchedStock existingStock = repository.findBySymbolIgnoreCase(symbol)
                .orElseThrow(() -> new IllegalArgumentException("Stock Not Found: " + symbol));
        repository.delete(existingStock);
        return "Deleted stock: " + symbol;
    }

    // Endpoint to delete all stocks from the watchlist
    @DeleteMapping
    public ResponseEntity<String> deleteAllStocks() {
        repository.deleteAll();
        return ResponseEntity.ok("All stocks deleted from watchlist.");
    }
    @GetMapping("/search")
    public List<WatchedStock> searchByName(@RequestParam String query) {
        return repository.findByNameContainingIgnoreCase(query);
}

}
