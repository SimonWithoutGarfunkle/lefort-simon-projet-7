package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RatingException;
import com.nnk.springboot.service.RatingService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class RatingController {
	
    @Autowired
    private RatingService ratingService;
    
    private static Logger logger = LoggerFactory.getLogger(RatingController.class);

    @RequestMapping("/rating/list")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String remoteUser = authentication.getName();
        model.addAttribute("remoteUser", remoteUser);
    	
    	List<Rating> ratings = ratingService.getAllRatings();
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
    	try {
    		ratingService.validateRating(rating);
    	} catch (RatingException e) {
    		logger.error(e.getMessage());
    		model.addAttribute("errorMessage", e.getMessage());
    		return "/rating/add";
    	}
    	
    	ratingService.addRating(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{ratingId}")
    public String showUpdateForm(@PathVariable("ratingId") Integer ratingId, Model model) {
        Rating ratingToUpdate = ratingService.getRatingById(ratingId);
        model.addAttribute("rating", ratingToUpdate);
        return "rating/update";
    }

    @PostMapping("/rating/update/{ratingId}")
    public String updateRating(@PathVariable("ratingId") Integer ratingId, @Valid Rating rating,
                             BindingResult result, Model model) {
    	try {
    		ratingService.validateRating(rating);
    	} catch (RatingException e) {
    		logger.error(e.getMessage());
    		model.addAttribute("errorMessage", e.getMessage());
    		return "rating/update";
    	}
    	ratingService.updateRating(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
