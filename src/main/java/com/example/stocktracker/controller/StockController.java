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
import com.example.stocktracker.Exceptions.*;

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
            throw new EmptyWatchListException();
        }
        return mapper.toDtoList(stocks);
    }

    // Endpoint to get a stock by its symbol
    @GetMapping("/{symbol}")
    public WatchedStockResponseDTO getStockBySymbol(@PathVariable String symbol) {
        Optional<WatchedStock> stockOptional = repository.findBySymbolIgnoreCase(symbol);
        if(stockOptional.isEmpty()){
            throw new StockNotFoundException(symbol);
        }
        WatchedStock stock = stockOptional.get();
        return mapper.toDto(stock);
    }

    // Endpoints to add Stocks to Rep
    @PostMapping
    public WatchedStockResponseDTO addStock(@Valid @RequestBody WatchedStockRequestDTO dto) {
        WatchedStock stock = mapper.toEntity(dto);
        String symbol = stock.getSymbol();
        Optional<WatchedStock> stockOptional = repository.findBySymbolIgnoreCase(symbol);
        if(stockOptional.isEmpty()){
            throw new StockAlreadyExistsException(symbol);
        }
        WatchedStock saved = repository.save(stock);
        return mapper.toDto(saved);
    }

    // Endpoint to update a stock by its symbol
    @PutMapping("/{symbol}")
    public WatchedStockResponseDTO updateStock(@Valid @PathVariable String symbol, @RequestBody WatchedStock stock) {
        Optional<WatchedStock> existingStock = repository.findBySymbolIgnoreCase(symbol);
        if(existingStock.isEmpty()){
            throw new StockNotFoundException(symbol);
        }
        WatchedStock existstock = existingStock.get();
        //Added ErrorHandling, but doesnt actually update Stock as of yet *FIX*


        return mapper.toDto(existstock);
    }

    //Endpoint to query stock by its name
    @GetMapping("/search")
    public WatchedStockResponseDTO searchByName(@RequestParam String query) {
        Optional<WatchedStock> existingStock = repository.findByNameContainingIgnoreCase(query);
        if(existingStock.isEmpty()){
            throw new StockNotFoundException(query);
        }
        WatchedStock stock = existingStock.get();
        return mapper.toDto(stock);
        
    }

    // Endpoint to delete a stock by its symbol
    @DeleteMapping("/{symbol}")
    public String deleteStock(@PathVariable String symbol) {
        Optional<WatchedStock> existingStock = repository.findByNameContainingIgnoreCase(symbol);
        if(existingStock.isEmpty()){
            throw new StockNotFoundException(symbol);
        }
        WatchedStock stock = existingStock.get();
        repository.delete(stock);
        return symbol + "Deleted";
    }

    // Endpoint to delete all stocks from the watchlist
    @DeleteMapping
    public ResponseEntity<String> deleteAllStocks() {
        repository.deleteAll();
        return ResponseEntity.ok("All stocks have been deleted from watchlist.");
    }
}
