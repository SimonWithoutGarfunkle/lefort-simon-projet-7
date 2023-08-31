package com.nnk.springboot.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;

public class TradeServiceTest {
	
	@Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;
    
    private Trade trade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("testAccount");
        when(tradeRepository.save(trade)).thenReturn(trade);
    }
    
    @Test
    void testConvertOptionalToTradeWithNonNullValue() {
        //Arrange
        Optional<Trade> optionalTrade = Optional.of(trade);

        //Act
        Trade result = tradeService.convertOptionalToTrade(optionalTrade);

        //Assert
        assertEquals(trade, result);
    }

    @Test
    void testConvertOptionalToTradeWithNullValue() {
        //Arrange
        Optional<Trade> optionalTrade = Optional.empty();

        //Act
        Trade result = tradeService.convertOptionalToTrade(optionalTrade);

        //Assert
        assertNull(result);
    }
    
    @Test
    void testGetTradeById() {
        //Arrange
        Optional<Trade> optionalTrade = Optional.ofNullable(trade);
    	when(tradeRepository.findById(anyInt())).thenReturn(optionalTrade);
    	
    	//Act
    	Trade result = tradeService.getTradeById(1);

        //Assert
    	verify(tradeRepository).findById(1);
    	assertEquals(trade, result);
    	
    }
    
    
    
    @Test
    void testUpdateTrade() {
        //Act
        Trade updatedTrade = tradeService.updateTrade(trade);

        //Assert
        verify(tradeRepository).save(trade);
        assertEquals("testAccount", updatedTrade.getAccount());
        
    }

    @Test
    void testDeleteTrade() {
        //Act
    	tradeService.deleteTrade(1);

        //Assert
    	verify(tradeRepository).deleteById(1);
    }
    
    @Test
    void testGetAllTrades() {
        // Arrange
        Trade trade1 = new Trade();
        trade1.setTradeId(1);
        Trade trade2 = new Trade();
        trade2.setTradeId(2);
        List<Trade> trades = Arrays.asList(trade1, trade2);
        when(tradeRepository.findAll()).thenReturn(trades);

        // Act
        List<Trade> result = tradeService.getAllTrades();

        // Assert
        assertEquals(trades.size(), result.size());
        assertEquals(trades.get(0), result.get(0));
        assertEquals(trades.get(1), result.get(1));
    }

    @Test
    void testAddTrade() {
        // Arrange
        Trade newTrade = new Trade();
        newTrade.setTradeId(2);
        newTrade.setAccount("newAccount");
        newTrade.setStatus("newTestStatus");
        when(tradeRepository.save(newTrade)).thenReturn(newTrade);

        // Act
        Trade addedTrade = tradeService.addTrade(newTrade);

        // Assert
        verify(tradeRepository).save(newTrade);
        assertEquals(newTrade, addedTrade);
    }

}
