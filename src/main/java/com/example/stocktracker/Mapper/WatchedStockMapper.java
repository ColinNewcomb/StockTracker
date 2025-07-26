package com.example.stocktracker.Mapper;

import com.example.stocktracker.model.WatchedStock;
import com.example.stocktracker.DTO.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WatchedStockMapper {
    
    WatchedStockMapper INSTANCE = Mappers.getMapper(WatchedStockMapper.class);
    WatchedStock toEntity(WatchedStockRequestDTO dto);
    WatchedStockResponseDTO toDto(WatchedStock stock);
    List<WatchedStockResponseDTO> toDtoList(List<WatchedStock> stocks);
}
