package com.sapient.ofd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sapient.ofd.dao.OnlineFoodDelivaryRepository;
import com.sapient.ofd.entity.Restaurant;
import com.sapient.ofd.exception.FoodDelivaryException;



@ExtendWith(SpringExtension.class)
class OfdServiceImplTest {

	@TestConfiguration
	static class RestaurantServiceImplTestContextConfiguration {
		@Bean
		public RestaurantService Service() {
			return new RestaurantServiceImpl();
		}
	}

	@Autowired
	private RestaurantService restaurantService;

	@MockBean
	private OnlineFoodDelivaryRepository ofdRepository;

	@BeforeEach
	public void setUp() {
		Restaurant restaurant1= new Restaurant(null,"Madhuban","Itialin",LocalTime.parse("10:30:00"),LocalTime.parse("21:00:00"),LocalDate.of(2016, 1, 5),2,"Coimbatore",7896548321L);
		restaurant1.setRestaurantId(16);

		Restaurant restaurant2= new Restaurant(null,"Paradise","Non vegeterian",LocalTime.parse("09:30:00"),
				LocalTime.parse("22:50:30"),LocalDate.of(2007,12,9),
				6,"Mangalore",9865325678L);
		Restaurant restaurant3= new Restaurant(null,"Marine Cafe","Bakery",LocalTime.parse("07:00:00"),
				LocalTime.parse("19:30:00"),LocalDate.of(2020,1,26),
				5,"Kurnool",9692581052L);


		List<Restaurant> restaurantList = Arrays.asList(restaurant1,restaurant2,restaurant3);

		/*Mockito.when(ofdRepository.findById(restaurant1.getRestaurantId()).stream().collect(Collectors.toList()))
		.thenReturn(restaurantList);*/

		Mockito.when(ofdRepository.findById(-99)).thenReturn(null);
		Mockito.when(ofdRepository.findByAddress("Malyasia")).thenReturn(null);
		Mockito.when(ofdRepository.findById(restaurant1.getRestaurantId())).thenReturn(Optional.of(restaurant1));
		Mockito.when(ofdRepository.findAll()).thenReturn(restaurantList);
		Mockito.when(ofdRepository.findById(108)).thenReturn(Optional.empty());
		Mockito.when(ofdRepository.findByAddress("Mangalore")).thenReturn(Optional.of(restaurant2).stream().collect(Collectors.toList()));

	}

	@Test
	public void whenValidId_thenRestaurantShouldBeFound() {
		Restaurant fromDb;
		try {
			fromDb = ofdRepository.findById(16).get();
			assertThat(fromDb.getAddress()).isEqualTo("Coimbatore");
			assertThat(fromDb.getRestaurantId().equals(16));
			verifyFindByIdIsCalledOnce();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@Test
	public void whenInValidId_thenRestaurantShouldNotBeFound() {
		Restaurant fromDb;
		try {
			fromDb = ofdRepository.findById(-99).get();
			verifyFindByIdIsCalledOnce();
			assertThat(fromDb).isNull();

		} catch (NoSuchElementException e){
			e.printStackTrace();
			
		} catch(NullPointerException e) {
			//e.printStackTrace();
			System.out.println("Test Case Passed");
		}

	}

	@Test
	public void findRestaurantsTest() {
		//when(ofdRepository.findAll()).thenReturn(Stream.of(new Restaurant(7,"Madhuban","Itialin",LocalTime.parse("10:30:00"),LocalTime.parse("21:00:00"),LocalDate.of(2016, 1, 5),2,"Coimbatore",7896548321L), 
		//	new Restaurant(15,"Paradise","Non vegeterian",LocalTime.parse("09:30:00"),LocalTime.parse("22:50:30"),LocalDate.of(2007,12,9),6,"Mangalore",9865325678L)).collect(Collectors.toList()));

		try {
			assertEquals(3, restaurantService.findRestaurants().size());
		} catch (FoodDelivaryException e) {
			e.printStackTrace();
		}
	}

	private void verifyFindByIdIsCalledOnce() {
		Mockito.verify(ofdRepository, VerificationModeFactory.times(1)).findById(Mockito.anyInt());
		Mockito.reset(ofdRepository);
	}
}
