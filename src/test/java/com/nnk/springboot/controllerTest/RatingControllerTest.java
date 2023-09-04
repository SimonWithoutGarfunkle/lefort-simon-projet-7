package com.nnk.springboot.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RatingControllerTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	private RatingService ratingService;
	
	Rating rating;
	Integer id;
	
	@BeforeEach
	public void setUp() {
		rating = new Rating(1, 2, 3, 4);
		rating = ratingService.addRating(rating);
		id = rating.getRatingId();				
	}
	
	@Test
	@WithMockUser
	public void homeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser
	public void addRatingFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("rating/add"));
	}
	
	@Test
	@WithMockUser
	public void validateRatingTest() throws Exception {	
		int resultBefore = ratingService.getAllRatings().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate").with(csrf())
				.param("moodysRating", "5")
				.param("sandPRating", "5")
				.param("fitchRating", "5")
				.param("orderNumber", "777"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list")).andDo(print());
		
		int resultAfter = ratingService.getAllRatings().size();
		assertTrue(resultAfter == resultBefore + 1);
			
	}
	
	@Test
	@WithMockUser
	public void validateRatingTest_NotValid() throws Exception {	
		int resultBefore = ratingService.getAllRatings().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate").with(csrf())
				.param("moodysRating", "99")
				.param("sandPRating", "5")
				.param("fitchRating", "aze")
				.param("orderNumber", "777"))
		.andExpect(MockMvcResultMatchers.view().name("/rating/add"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));			
	}
	
	@Test
	@WithMockUser
	public void showUpdateFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
		.andExpect(MockMvcResultMatchers.view().name("rating/update"));
	}
	
	@Test
	@WithMockUser
	public void updateRatingTest() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/"+id).with(csrf())
				.param("moodysRating", "5")
				.param("sandPRating", "5")
				.param("fitchRating", "5")
				.param("orderNumber", "777"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list")).andDo(print());
		Rating result = ratingService.getRatingById(id);
		assertTrue(result.getSandPRating().equals(5));
		
	}
	
	@Test
	@WithMockUser
	public void deleteRatingTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/"+id))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
		
		assertNull(ratingService.getRatingById(id));
	}

}
