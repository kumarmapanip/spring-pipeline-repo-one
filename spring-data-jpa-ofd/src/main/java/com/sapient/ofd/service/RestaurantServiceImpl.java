package com.sapient.ofd.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapient.ofd.dao.OnlineFoodDelivaryRepository;
import com.sapient.ofd.entity.Restaurant;
import com.sapient.ofd.exception.FoodDelivaryException;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService{
	@Autowired
	private OnlineFoodDelivaryRepository  ofdRepo;

	@Override
	public Restaurant findRestaurantById(Integer restaurantID) throws FoodDelivaryException {
		try {
			Optional<Restaurant> optional = 
									ofdRepo.findById(restaurantID);
			if(optional.isPresent()) {
				return optional.get();
			}else {
				throw new PersistenceException("Invalid ID");
			}			
			
		}catch(DataAccessException e) {
			throw new FoodDelivaryException(e.getMessage(),e);
		}
	}


	@Override
	public List<Restaurant> findRestaurants() throws FoodDelivaryException {
		try {
			List<Restaurant> restaurantList = ofdRepo.findAll();
			//business rules
			return restaurantList;
		}catch(DataAccessException e) {
			throw new FoodDelivaryException(e.getMessage(),e);
		}
	}

	@Override
	public Restaurant addRestaurant(Restaurant restaurant) throws FoodDelivaryException {
		try {
			//validations
			RestaurantValidator validator= new RestaurantValidator();
			if(validator.isValidEstablishedDate(restaurant)
					&& validator.isValidRating(restaurant)) {
				restaurant.setRestaurantId(null);
				return ofdRepo.save(restaurant);

			}else {
				throw new FoodDelivaryException("Restaurant established date is invalid");
			}						

		}catch(DataAccessException e) {
			throw new FoodDelivaryException(e.getMessage(),e);
		}
	}

	@Override
	public Integer deleteRestaurant(Integer restaurantID) throws FoodDelivaryException {
		try {
			ofdRepo.deleteById(restaurantID);
			return restaurantID;

		}catch(DataAccessException e) {
			throw new FoodDelivaryException(e.getMessage(),e);
		}
	}

	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) throws FoodDelivaryException {
		try {
			return ofdRepo.save(restaurant);			

		}catch(DataAccessException e) {
			throw new FoodDelivaryException(e.getMessage(),e);
		}
	}

	@Override
	public List<Restaurant> findRestaurantsEstablishedAfter(LocalDate estDate) throws FoodDelivaryException{
		try {
			return ofdRepo.findRestaurantsEstablishedAfter(estDate);		

		}catch(DataAccessException e) {
			throw new FoodDelivaryException(e.getMessage(),e);
		}
	}

}
