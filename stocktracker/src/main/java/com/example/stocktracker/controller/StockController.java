package com.example.stocktracker.controller;

import com.example.stocktracker.model.WatchedStock;
import com.example.stocktracker.repository.WatchedStockRep;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class StockController {

    private final WatchedStockRep repository;

    public StockController(WatchedStockRep repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<WatchedStock> getAll() {
        return repository.findAll();
    }
    @GetMapping("/{symbol}")
    public ResponseEntity<WatchedStock> getStockBySymbol(@PathVariable String symbol) {
        List<WatchedStock> matches = repository.findAll()
        .stream()
        .filter(s -> s.getSymbol().equalsIgnoreCase(symbol))
        .toList();

        if (matches.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(null);
        }

        return ResponseEntity.ok(matches.get(0));
    }

    @PostMapping
    public ResponseEntity<Object> addStock(@RequestBody WatchedStock stock) {
        boolean exists = repository.findAll().stream()
        .anyMatch(s -> s.getSymbol().equalsIgnoreCase(stock.getSymbol()));

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Stock already exists: " + stock.getSymbol());
}
        return ResponseEntity.ok(repository.save(stock));
    }

    @PutMapping("/{symbol}")
    public ResponseEntity<WatchedStock> updateStock(@PathVariable String symbol, @RequestBody WatchedStock stock) {
        List<WatchedStock> matches = repository.findAll()
        .stream()
        .filter(s -> s.getSymbol().equalsIgnoreCase(symbol))
        .toList();

        if (matches.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(null);
        }

        WatchedStock existingStock = matches.get(0);
        existingStock.setName(stock.getName());

        //existingStock.setPrice(stock.getPrice());
        // Update other fields as necessary

        WatchedStock updatedStock = repository.save(existingStock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<String> deleteStock(@PathVariable String symbol) {
        List<WatchedStock> matches = repository.findAll()
        .stream()
        .filter(s -> s.getSymbol().equalsIgnoreCase(symbol))
        .toList();

        if (matches.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Stock not found: " + symbol);
    }

        repository.deleteAll(matches);
        return ResponseEntity.ok("Deleted stock: " + symbol);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllStocks() {
        repository.deleteAll();
        return ResponseEntity.ok("All stocks deleted from watchlist.");
    }
}
