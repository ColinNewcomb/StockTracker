package com.example.stocktracker.service;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.stereotype.Service;
import com.example.stocktracker.DTO.WatchedStockRequestDTO;
import com.example.stocktracker.DTO.WatchedStockResponseDTO;
import com.example.stocktracker.Exceptions.EmptyWatchListException;
import com.example.stocktracker.Exceptions.RepositoryEmptyException;
import com.example.stocktracker.Exceptions.StockAlreadyExistsException;
import com.example.stocktracker.Exceptions.StockNotFoundException;
import com.example.stocktracker.Mapper.WatchedStockMapper;
import com.example.stocktracker.client.DTO.CompanyInfoDTO;
import com.example.stocktracker.client.DTO.StockPriceDTO;
import com.example.stocktracker.model.WatchedStock;
import com.example.stocktracker.repository.WatchedStockRep;
import com.example.stocktracker.client.StockApiClient;


import java.util.*;


@Service
public class StockService {

    private final WatchedStockRep repository;  // Private field
    private final WatchedStockMapper mapper;   // Private field
    private final StockApiClient stockApiClient;
    
    // Constructor - Spring will automatically inject these
    public StockService(WatchedStockRep repository, WatchedStockMapper mapper,StockApiClient stockApiClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.stockApiClient = stockApiClient;
    }

    public List<WatchedStockResponseDTO> getAllStocks(){
		List<WatchedStock> stocks = repository.findAll();
        if (stocks.isEmpty()) {
            throw new EmptyWatchListException();
        }
        return mapper.toDtoList(stocks);
	}

    public WatchedStockResponseDTO getStockBySymbol(String Symbol){
        Optional<WatchedStock> stockOptional = repository.findBySymbolIgnoreCase(Symbol);
        if(stockOptional.isEmpty()){
            throw new StockNotFoundException(Symbol);
        }
        WatchedStock stock = stockOptional.get();
        return mapper.toDto(stock);
    }

    public WatchedStockResponseDTO addstock(WatchedStockRequestDTO dto){
        String symbol = dto.getSymbol();

        CompanyInfoDTO companyInfo = stockApiClient.getCompanyInfo(symbol);
        StockPriceDTO currentPrice = stockApiClient.getCurrentPrice(symbol);
        
        Optional<WatchedStock> stockOptional = repository.findBySymbolIgnoreCase(symbol);
        if(stockOptional.isPresent()){
            throw new StockAlreadyExistsException(symbol);
        }
        WatchedStock stock = new WatchedStock();
        stock.setSymbol(companyInfo.getSymbol());
        stock.setName(companyInfo.getName());        // Real company name
        stock.setPrice(currentPrice.getPrice());

        WatchedStock saved = repository.save(stock);
        return mapper.toDto(saved);
    }

    public WatchedStockResponseDTO updateStock(String symbol,WatchedStock stock){
        Optional<WatchedStock> existingStock = repository.findBySymbolIgnoreCase(symbol);
        if(existingStock.isEmpty()){
            throw new StockNotFoundException(symbol);
        }
        WatchedStock existstock = existingStock.get();
        
        StockPriceDTO currentPrice = stockApiClient.getCurrentPrice(symbol);

        existstock.setPrice(currentPrice.getPrice());
        repository.save(existstock);
        return mapper.toDto(existstock);
    }

    public WatchedStockResponseDTO searchByName(String query){
        Optional<WatchedStock> existingStock = repository.findByNameContainingIgnoreCase(query);
        if(existingStock.isEmpty()){
            throw new StockNotFoundException(query);
        }
        WatchedStock stock = existingStock.get();
        return mapper.toDto(stock);
    }

    public String deleteStock(String symbol){
        Optional<WatchedStock> existingStock = repository.findBySymbolIgnoreCase(symbol);
        if(existingStock.isEmpty()){
            throw new StockNotFoundException(symbol);
        }
        WatchedStock stock = existingStock.get();
        repository.delete(stock);
        return symbol + " Has Been Deleted";
    }

    public String deleteAllStocks() {
        Optional<WatchedStock> existingStock = repository.findAll().stream().findAny();
        if(existingStock.isEmpty()){
            throw new RepositoryEmptyException();
        }
        repository.deleteAll();
        String message = "All stocks have been deleted from your watchlist.";
        return message;
    }
}
