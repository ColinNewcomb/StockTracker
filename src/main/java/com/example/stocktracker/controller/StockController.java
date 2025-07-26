package com.example.stocktracker.controller;

import com.example.stocktracker.DTO.WatchedStockRequestDTO;
import com.example.stocktracker.DTO.WatchedStockResponseDTO;
import com.example.stocktracker.Mapper.WatchedStockMapper;
import com.example.stocktracker.model.WatchedStock;
import com.example.stocktracker.repository.WatchedStockRep;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/watchlist")
public class StockController {

    private final WatchedStockRep repository;
    @Autowired
    private WatchedStockMapper mapper;

    // Constructor injection for the repository
    public StockController(WatchedStockRep repository) {
        this.repository = repository;
    }

    // Endpoint to get all watched stocks
    @GetMapping
    public List<WatchedStockResponseDTO> getAll() {
        List<WatchedStock> stocks = repository.findAll();
        if (stocks.isEmpty()) {
            throw new IllegalArgumentException("No stocks found in the watchlist.");
        }
        return mapper.toDtoList(stocks);
    }

    // Endpoint to get a stock by its symbol
    @GetMapping("/{symbol}")
    public ResponseEntity<WatchedStockResponseDTO> getStockBySymbol(@PathVariable String symbol) {
        Optional<WatchedStock> stockOptional = repository.findBySymbolIgnoreCase(symbol);
        WatchedStock stock = stockOptional.get();
        WatchedStockResponseDTO response = mapper.toDto(stock);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Endpoints to add Stocks to Rep
    @PostMapping
    public ResponseEntity<WatchedStockResponseDTO> addStock(@Valid @RequestBody WatchedStockRequestDTO dto) {
        WatchedStock stock = mapper.toEntity(dto);
        WatchedStock saved = repository.save(stock);
        WatchedStockResponseDTO response = mapper.toDto(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint to update a stock by its symbol
    @PutMapping("/{symbol}")
    public ResponseEntity<WatchedStockResponseDTO> updateStock(@Valid @PathVariable String symbol, @RequestBody WatchedStock stock) {
        WatchedStock existingStock = repository.findBySymbolIgnoreCase(symbol)
        .orElseThrow(() -> new IllegalArgumentException("Stock Not Found: " + symbol));
    
        WatchedStockResponseDTO response = mapper.toDto(existingStock);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Endpoint to query stock by its name
    @GetMapping("/search")
    public ResponseEntity<WatchedStockResponseDTO> searchByName(@RequestParam String query) {
        WatchedStock existingStock = repository.findByNameContainingIgnoreCase(query);
        WatchedStockResponseDTO response = mapper.toDto(existingStock);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
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
}
