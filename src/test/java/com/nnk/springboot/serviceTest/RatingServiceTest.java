package com.nnk.springboot.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;

public class RatingServiceTest {
	
	@Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;
    
    private Rating rating;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = new Rating();
        rating.setRatingId(1);
        rating.setFitchRating("New Value");
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
    }
    
    @Test
    void testConvertOptionalToRatingWithNonNullValue() {
        //Arrange
        Optional<Rating> optionalRating = Optional.of(rating);

        //Act
        Rating result = ratingService.convertOptionalToRating(optionalRating);

        //Assert
        assertEquals(rating, result);
    }

    @Test
    void testConvertOptionalToRatingWithNullValue() {
        //Arrange
        Optional<Rating> optionalRating = Optional.empty();

        //Act
        Rating result = ratingService.convertOptionalToRating(optionalRating);

        //Assert
        assertNull(result);
    }
    
    @Test
    void testgetRatingById() {
        //Arrange
        Optional<Rating> optionalRating = Optional.ofNullable(rating);
    	when(ratingRepository.findById(anyInt())).thenReturn(optionalRating);
    	
    	//Act
    	Rating result = ratingService.getRatingById(1);

        //Assert
    	verify(ratingRepository).findById(1);
    	assertEquals(rating, result);
    	
    }
    
    
    
    @Test
    void testUpdateRating() {
        //Act
        Rating updatedRating = ratingService.updateRating(rating);

        //Assert
        verify(ratingRepository).save(rating);
        assertEquals("New Value", updatedRating.getFitchRating());
        
    }

    @Test
    void testDeleteRating() {
        //Act
    	ratingService.deleteRating(1);

        //Assert
    	verify(ratingRepository).deleteById(1);
    }

}
