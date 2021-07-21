package com.sapient.ofd.service;

import java.time.LocalDate;

import com.sapient.ofd.entity.Restaurant;

public class RestaurantValidator {
	public Boolean isValidEstablishedDate(Restaurant restaurant) {
		if(restaurant.getEstablishedDate().isAfter(LocalDate.now())) {
			return false;
		}
		return true;
	}

	public Boolean isValidRating(Restaurant restaurant) {
		int rating=restaurant.getRating();
		if(rating>=1 && rating<=7) {
			return true;
		}
		return false;
	}

	//TO DO
}
