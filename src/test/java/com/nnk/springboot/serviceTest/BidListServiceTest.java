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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;

public class BidListServiceTest {
	
	@Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;
    
    private BidList bidList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setBidQuantity(9d);
        when(bidListRepository.save(bidList)).thenReturn(bidList);
    }
    
    @Test
    void testConvertOptionalToBidListWithNonNullValue() {
        //Arrange
        Optional<BidList> optionalBidList = Optional.of(bidList);

        //Act
        BidList result = bidListService.convertOptionalToBidList(optionalBidList);

        //Assert
        assertEquals(bidList, result);
    }

    @Test
    void testConvertOptionalToBidListWithNullValue() {
        //Arrange
        Optional<BidList> optionalBidList = Optional.empty();

        //Act
        BidList result = bidListService.convertOptionalToBidList(optionalBidList);

        //Assert
        assertNull(result);
    }
    
    @Test
    void testGetBidListById() {
        //Arrange
        Optional<BidList> optionalBidList = Optional.ofNullable(bidList);
    	when(bidListRepository.findById(anyInt())).thenReturn(optionalBidList);
    	
    	//Act
    	BidList result = bidListService.getBidListById(1);

        //Assert
    	verify(bidListRepository).findById(1);
    	assertEquals(bidList, result);
    	
    }
    
    
    
    @Test
    void testUpdateBidList() {
        //Act
        BidList updatedBidList = bidListService.updateBidList(bidList);

        //Assert
        verify(bidListRepository).save(bidList);
        assertEquals(9d, updatedBidList.getBidQuantity());
        
    }

    @Test
    void testDeleteBidList() {
        //Act
    	bidListService.deleteBidList(1);

        //Assert
    	verify(bidListRepository).deleteById(1);
    }
    
    @Test
    void testGetAllBidLists() {
        // Arrange
        BidList bidList1 = new BidList();
        bidList1.setBidListId(1);
        BidList bidList2 = new BidList();
        bidList2.setBidListId(2);
        List<BidList> bidLists = Arrays.asList(bidList1, bidList2);
        when(bidListRepository.findAll()).thenReturn(bidLists);

        // Act
        List<BidList> result = bidListService.getAllBidLists();

        // Assert
        assertEquals(bidLists.size(), result.size());
        assertEquals(bidLists.get(0), result.get(0));
        assertEquals(bidLists.get(1), result.get(1));
    }

    @Test
    void testAddBidList() {
        // Arrange
        BidList newBidList = new BidList();
        newBidList.setBidListId(2);
        newBidList.setAccount("New BidList Account");
        newBidList.setBidQuantity(88d);
        when(bidListRepository.save(newBidList)).thenReturn(newBidList);

        // Act
        BidList addedBidList = bidListService.addBidList(newBidList);

        // Assert
        verify(bidListRepository).save(newBidList);
        assertEquals(newBidList, addedBidList);
    }

}
