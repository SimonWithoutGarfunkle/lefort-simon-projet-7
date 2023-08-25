package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

/**
 * Implementation the CRUD for the Rating Entity
 * 
 * @author Simon
 *
 */
@Service
public class RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
	
	private static Logger logger = LoggerFactory.getLogger(RatingService.class);
	
	/**
	 * Extract all Ratings from the DataBase
	 * 
	 * @return List of all ratings from the DataBase
	 */
	public List<Rating> getAllRatings() {
		logger.info("call getAllRatings");
		return ratingRepository.findAll();
	}
	
	/**
	 * Convert Optional<Rating> to Rating
	 * 
	 * @param option to test
	 * @return Rating or null
	 */
	public Rating convertOptionalToRating(Optional<Rating> option) {
		logger.info("call convertOptionalToRating");
		return option.orElse(null);
		
	}
	
	/**
	 * Extract the rating corresponding to the Id from DB
	 * 
	 * @param id of the Rating
	 * @return Rating
	 */
	public Rating getRatingById(Integer id) {
		logger.info("call getRatingById");		
		return convertOptionalToRating(ratingRepository.findById(id));
	}
	
	/**
	 * Add rating to DB
	 * 
	 * @param rating
	 * @return saved rating
	 */
	public Rating addRating(Rating rating) {
		logger.info("call updateRating");	
		return ratingRepository.save(rating);
	}
	
	
	/**
	 * Update an existing rating
	 * 
	 * @param rating
	 * @return updated rating
	 */
	public Rating updateRating(Rating rating) {
		logger.info("call updateRating");	
		return ratingRepository.save(rating);
	}
	
	/**
	 * Delete the rating corresponding to the id
	 * 
	 * @param id of the rating to delete
	 */
	public void deleteRating(Integer id) {
		logger.info("call deleteRating");	
		ratingRepository.deleteById(id);
		
	}
	
	
	
	
	
	

}