package com.sapient.ofd.service;

import java.time.LocalDate;
import java.util.List;


import com.sapient.ofd.entity.Restaurant;
import com.sapient.ofd.exception.FoodDelivaryException;

public interface RestaurantService {
	public abstract Restaurant findRestaurantById(Integer restaurantID) throws FoodDelivaryException;
	public abstract List<Restaurant> findRestaurants() throws FoodDelivaryException;
	public abstract Restaurant addRestaurant(Restaurant restaurant) throws FoodDelivaryException;
	public abstract Integer deleteRestaurant(Integer restaurantID) throws FoodDelivaryException;
	public abstract Restaurant updateRestaurant(Restaurant restaurant) throws FoodDelivaryException;
	public abstract List<Restaurant> findRestaurantsEstablishedAfter(LocalDate estDate) throws FoodDelivaryException;
}
