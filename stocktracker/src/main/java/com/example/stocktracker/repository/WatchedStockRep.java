package com.example.stocktracker.repository;

import com.example.stocktracker.model.WatchedStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchedStockRep extends JpaRepository<WatchedStock, Long> {
    void deleteBySymbol(String symbol);
}