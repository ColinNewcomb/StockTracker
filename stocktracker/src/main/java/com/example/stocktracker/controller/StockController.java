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

    @PostMapping
    public WatchedStock addStock(@RequestBody WatchedStock stock) {
        return repository.save(stock);
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

}
