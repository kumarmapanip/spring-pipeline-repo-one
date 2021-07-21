package com.sapient.ofd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sapient.ofd.entity.Restaurant;
import com.sapient.ofd.exception.FoodDelivaryException;
import com.sapient.ofd.service.RestaurantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 
 * @author Admin
 * REST API end-points 
 *   GET: http://localhost:8081/ofd/v1/restaurant/1
 *   GET: http://localhost:8081/ofd/v1/restaurant
 *   POST:  http://localhost:8081/ofd/v1/restaurant
 *   PUT : http://localhost:8081/ofd/v1/restaurant
 *   DELETE: http://localhost:8081/ofd/v1/restaurant/1
 */
//allow all origins to access
//@CrossOrigin
//same as
@CrossOrigin(origins = "*")
//not required as we are using Spring AOP
//@Slf4j
@RestController
@RequestMapping("/v1")
@Api
public class OfdController {
	@Autowired
	private RestaurantService restaurantService;



	//http://localhost:8081/ofd/v1/restaurant/1
	@ApiOperation(value = "Find Restaurant By ID",
			consumes = "Userid",
			produces = "Restaurant object",
			response = Restaurant.class,
			tags = "findById",
			notes = "http://localhost:8081/ofd/v1/restaurant/1"
			)
	@RequestMapping("/restaurant/{id}")
	public  ResponseEntity<Restaurant> findRestaurantById(@PathVariable("id") Integer id){
		try {			
			Restaurant restaurant = restaurantService.findRestaurantById(id);
			//			log.info(restaurant.toString());
			return new ResponseEntity<>(restaurant,HttpStatus.OK);
		}catch(FoodDelivaryException e) {
			//			log.error(e.getMessage(),e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}

	//http://localhost:8081/ofd/v1/restaurant
	//	@PostMapping("/restaurant")
	//	public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant,
	//			BindingResult bindingResult) {
	//		try {			
	//			if(bindingResult.hasErrors()) {
	//				throw new FoodDelivaryException(bindingResult.getAllErrors().toString());
	//			}
	//			Restaurant restaurantFromDB= restaurantService.addRestaurant(restaurant);
	//			log.info("A New Restaurant Added: "+ restaurantFromDB);
	//			return ResponseEntity.ok().body(restaurantFromDB);
	//					
	//		}catch(FoodDelivaryException e) {
	//			log.error(e.getMessage(),e);	
	//			throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
	//		}
	//	}

	//http://localhost:8081/ofd/v1/restaurant
	@ApiOperation(value = "Add Restaurant",
			consumes = "Restaurant object",
			produces = "Restaurant object",
			response = Restaurant.class,
			tags = "postRestaurant",
			notes = "http://localhost:8081/ofd/v1/restaurant"
			)
	@PostMapping("/restaurant")
	public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant,
			BindingResult bindingResult) throws FoodDelivaryException{

		if(bindingResult.hasErrors()) {
			throw new FoodDelivaryException(bindingResult.getAllErrors().toString());
		}
		Restaurant restaurantFromDB= restaurantService.addRestaurant(restaurant);
		//		log.info("A New Restaurant Added: "+ restaurantFromDB);
		return ResponseEntity.ok().body(restaurantFromDB);

	}



	@ApiOperation(value = "Find All Restaurants",			
			produces = "List of Restaurant objects",
			response = Restaurant.class,
			tags = "findRestaurants",
			notes = "http://localhost:8081/ofd/v1/restaurant"
			)
	@GetMapping("/restaurant")
	public ResponseEntity<List<Restaurant>> findRestaurants(){
		try {			
			List<Restaurant> restaurantList =
					restaurantService.findRestaurants();
			//		log.info(restaurantList.toString());
			return new ResponseEntity<>(restaurantList,HttpStatus.OK);
		}catch(FoodDelivaryException e) {
			//		log.error(e.getMessage(),e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}

	@ApiOperation(value = "Edit Restaurant",			
			produces = "Restaurant object",
			response = Restaurant.class,
			tags = "updateRestaurant",
			notes = "http://localhost:8081/ofd/v1/restaurant"
			)
	@PutMapping("/restaurant")
	public ResponseEntity<Restaurant> updateRestaurant(@Valid @RequestBody Restaurant restaurant,
			BindingResult bindingResult){
		try {			
			if(bindingResult.hasErrors()) {
				throw new FoodDelivaryException(bindingResult.getAllErrors().toString());
			}
			Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurant);
			//		log.info("Restaurant Updated: "+ updatedRestaurant);
			return ResponseEntity.ok(updatedRestaurant);
		}catch(FoodDelivaryException e) {
			//		log.error(e.getMessage(),e);
			throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
		}
	}

	@ApiOperation(value = "Delete Restaurant",			
			produces = "Restaurant Id of the deleted object",
			response = Integer.class,
			tags = "deleteRestaurant",
			notes = "http://localhost:8081/ofd/v1/restaurant/1"
			)
	@DeleteMapping("/restaurant/{id}")
	public ResponseEntity<Integer> deleteRestaurant(@PathVariable("id") Integer id){
		try {			

			Integer n = restaurantService.deleteRestaurant(id);
			//		log.info("Restaurant Deleted");
			return ResponseEntity.ok(n);
		}catch(FoodDelivaryException e) {
			//		log.error(e.getMessage(),e);
			throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
		}
	}
}
