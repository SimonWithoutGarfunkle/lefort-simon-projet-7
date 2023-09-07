package com.nnk.springboot.serviceTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RatingException;
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
        rating.setFitchRating(9);
        when(ratingRepository.save(rating)).thenReturn(rating);
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
    void testGetRatingById() {
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
        assertEquals(9, updatedRating.getFitchRating());
        
    }

    @Test
    void testDeleteRating() {
        //Act
    	ratingService.deleteRating(1);

        //Assert
    	verify(ratingRepository).deleteById(1);
    }
    
    @Test
    void testGetAllRatings() {
        // Arrange
        Rating rating1 = new Rating();
        rating1.setRatingId(1);
        Rating rating2 = new Rating();
        rating2.setRatingId(2);
        List<Rating> ratings = Arrays.asList(rating1, rating2);
        when(ratingRepository.findAll()).thenReturn(ratings);

        // Act
        List<Rating> result = ratingService.getAllRatings();

        // Assert
        assertEquals(ratings.size(), result.size());
        assertEquals(ratings.get(0), result.get(0));
        assertEquals(ratings.get(1), result.get(1));
    }

    @Test
    void testAddRating() {
        // Arrange
        Rating newRating = new Rating();
        newRating.setRatingId(2);
        newRating.setMoodysRating(8);
        newRating.setSandPRating(9);
        newRating.setFitchRating(10);
        newRating.setOrderNumber(200);
        when(ratingRepository.save(newRating)).thenReturn(newRating);

        // Act
        Rating addedRating = ratingService.addRating(newRating);

        // Assert
        verify(ratingRepository).save(newRating);
        assertEquals(newRating, addedRating);
    }

    @Test
    void testValidateRating_ValidData() {
        // Arrange
        Rating validRating = new Rating();
        validRating.setMoodysRating(5);
        validRating.setSandPRating(6);
        validRating.setFitchRating(7);
        validRating.setOrderNumber(100);

        // Act & Assert
        assertDoesNotThrow(() -> ratingService.validateRating(validRating));
    }

    @Test
    void testValidateRating_InvalidMoodysRating() {
        // Arrange
        Rating invalidRating = new Rating();
        invalidRating.setMoodysRating(-1);
        invalidRating.setSandPRating(6);
        invalidRating.setFitchRating(7);
        invalidRating.setOrderNumber(100);

        // Act & Assert
        assertThrows(RatingException.class, () -> ratingService.validateRating(invalidRating));
    }

    @Test
    void testValidateRating_InvalidSandPRating() {
        // Arrange
        Rating invalidRating = new Rating();
        invalidRating.setMoodysRating(5);
        invalidRating.setSandPRating(11);
        invalidRating.setFitchRating(7);
        invalidRating.setOrderNumber(100);

        // Act & Assert
        assertThrows(RatingException.class, () -> ratingService.validateRating(invalidRating));
    }

    @Test
    void testValidateRating_InvalidFitchRating() {
        // Arrange
        Rating invalidRating = new Rating();
        invalidRating.setMoodysRating(5);
        invalidRating.setSandPRating(6);
        invalidRating.setFitchRating(15);
        invalidRating.setOrderNumber(100);

        // Act & Assert
        assertThrows(RatingException.class, () -> ratingService.validateRating(invalidRating));
    }

    @Test
    void testValidateRating_InvalidOrderNumber() {
        // Arrange
        Rating invalidRating = new Rating();
        invalidRating.setMoodysRating(5);
        invalidRating.setSandPRating(6);
        invalidRating.setFitchRating(7);
        invalidRating.setOrderNumber(10000);

        // Act & Assert
        assertThrows(RatingException.class, () -> ratingService.validateRating(invalidRating));
    }

}
