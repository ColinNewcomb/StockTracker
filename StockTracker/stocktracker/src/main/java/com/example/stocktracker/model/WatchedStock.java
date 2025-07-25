package com.example.stocktracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchedStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private String name;
    private Double price;
    private Double change;
    private Double changePercent;
    private LocalDateTime addedAt = LocalDateTime.now();
}