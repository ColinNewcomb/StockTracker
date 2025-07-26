package com.example.stocktracker.repository;

import com.example.stocktracker.model.WatchedStock;
import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchedStockRep extends JpaRepository<WatchedStock, Long> {
    void deleteBySymbol(String symbol);
    WatchedStock findBySymbol(String symbol);
    Optional<WatchedStock> findBySymbolIgnoreCase(String symbol);
    boolean existsBySymbolIgnoreCase(String symbol);
    void deleteBySymbolIgnoreCase(String symbol);
    WatchedStock findByNameContainingIgnoreCase(String name);
    // Additional methods can be defined here if needed

}