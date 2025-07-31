package com.example.stocktracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.example.stocktracker.DTO.WatchedStockRequestDTO;
import com.example.stocktracker.DTO.WatchedStockResponseDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class StocktrackerApplicationTests {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void contextLoads() {
        // Basic test - keeps your existing test
    }
    
    @Test
    void shouldAddStock() {
        // Arrange
        WatchedStockRequestDTO request = new WatchedStockRequestDTO();
        request.setSymbol("AAPL");
        request.setName("Apple Inc");
        request.setPrice(150.0);
        
        // Act
        ResponseEntity<WatchedStockResponseDTO> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/watchlist", 
            request, 
            WatchedStockResponseDTO.class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("AAPL", response.getBody().getSymbol());
    }
    
    @Test
    void shouldGetAllStocks() {
        // Act
        ResponseEntity<WatchedStockResponseDTO[]> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/watchlist",
            WatchedStockResponseDTO[].class
        );
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}