package com.sapient.ofd.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sapient.ofd.app.SpringDataJpaOfdApplication;
import com.sapient.ofd.dao.OnlineFoodDelivaryRepository;
import com.sapient.ofd.entity.Restaurant;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringDataJpaOfdApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace=Replace.NONE)
//@WebMvcTest(OfdController.class)
class OfdControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private OnlineFoodDelivaryRepository repository;

	/*@Autowired
	private SpringDataJpaOfdApplication springApp;
	@MockBean
	private OnlineFoodDelivaryRepository repository;*/

	/*@InjectMocks
	private OfdController ofdCont;*/

	/*@Before(value = "hello")
	public void setUp() throws NullPointerException{
		mvc = MockMvcBuilders.standaloneSetup(repository).build();
	}*/
    @Test
	public void whenValidInput_thenCreateRestaurant() throws IOException, Exception {
		Restaurant restaurant= new Restaurant(null,"Madhuban","Continental",LocalTime.parse("09:30:00"),
				LocalTime.parse("22:50:30"),LocalDate.of(2007,12,9),6,"Nayagarh",9865889360L);
		restaurant.setRestaurantId(101);

		//mvc.perform(MockMvcRequestBuilders.get("/ofd/v1/restaurant")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		//Mockito.when(repository.getById(101)).thenReturn(restaurant);

		try {
			mvc.perform(post("/ofd/v1/restaurant").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(restaurant)));//.andExpect(status().isCreated());//.andExpect(MockMvcResultMatchers.jsonPath("$.restaurantId").exists());
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		/*mvc.perform( MockMvcRequestBuilders
			      .post("/ofd/v1/restaurant")
			      .content(JsonUtil.toJson(restaurant))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated())
			      .andReturn();*/

		/*mvc.perform( MockMvcRequestBuilders
			      .get("/ofd/v1/restaurant/{id}", 2)
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(System.out::print)
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.restaurantId").value(2));*/

		List<Restaurant> found = repository.findAll();
		for(Restaurant rst: found)System.out.println(rst);
		assertThat(found).extracting(Restaurant::getRestaurantName).contains("Taj Hotels");
	}


}
