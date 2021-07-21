package com.sapient.ofd.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sapient.ofd.entity.Restaurant;

public interface OnlineFoodDelivaryRepository extends JpaRepository<Restaurant, Integer>{
	
	//custom query methods
	List<Restaurant> findByAddress(String address);
	List<Restaurant> findTop5ByRating(Integer rating);// list of top 5 hotels whose rating matches with rating
	List<Restaurant> findByEstablishedDateBetween(LocalDate start, LocalDate end);
	//Named query methods
	@Query("select r from Restaurant r where establishedDate> :givenDate")
	List<Restaurant> findRestaurantsEstablishedAfter(@Param("givenDate") LocalDate estdDate);
	
}
