package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rating")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer ratingId;
	
	Integer moodysRating;
	
	Integer sandPRating;
	
	Integer fitchRating;
	
	Integer orderNumber;
	

	public Rating() {
		
	}
	
	public Rating(Integer moodysRating, Integer sandPRating, Integer fitchRating, Integer orderNumber) {
		super();
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}
	public Integer getRatingId() {
		return ratingId;
	}
	public void setRatingId(Integer id) {
		this.ratingId = id;
	}
	public Integer getMoodysRating() {
		return moodysRating;
	}
	public void setMoodysRating(Integer moodysRating) {
		this.moodysRating = moodysRating;
	}
	public Integer getSandPRating() {
		return sandPRating;
	}
	public void setSandPRating(Integer sandPRating) {
		this.sandPRating = sandPRating;
	}
	public Integer getFitchRating() {
		return fitchRating;
	}
	public void setFitchRating(Integer fitchRating) {
		this.fitchRating = fitchRating;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
