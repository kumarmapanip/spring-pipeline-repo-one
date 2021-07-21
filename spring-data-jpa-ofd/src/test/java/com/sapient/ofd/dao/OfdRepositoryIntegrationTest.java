package com.sapient.ofd.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sapient.ofd.app.SpringDataJpaOfdApplication;
import com.sapient.ofd.entity.Restaurant;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringDataJpaOfdApplication.class })

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class OfdRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private OnlineFoodDelivaryRepository repository;
	
    @Test
    public void whenFindByAddress_thenReturnAddress() {
        Restaurant restaurant = new Restaurant(null,"Madhuban","Itialin",LocalTime.parse("10:30:00"),LocalTime.parse("21:00:00"),LocalDate.of(2016, 1, 5),2,"Coimbatore",7896548321L);
        entityManager.persistAndFlush(restaurant);

        List<Restaurant> restaurantList = repository.findByAddress(restaurant.getAddress());
        if(restaurantList.size()!=0) {
        	assertThat(restaurantList.get(0).getAddress()).isEqualTo(restaurant.getAddress());
        }else {
        	assertThat(restaurantList.get(0).getAddress()).isNotEqualTo(restaurant.getAddress());
        }
    }

    @Test
    public void whenInvalidAddress_thenReturnNull() {
    	List<Restaurant> foundList = repository.findByAddress("Invalid Address");
        assertThat(foundList).isEmpty();
    }
    
    @Test
    public void whenFindById_thenReturnRestaurnt() {
    	Restaurant restaurant= new Restaurant(null,"Paradise","Non vegeterian",LocalTime.parse("09:30:00"),
    												LocalTime.parse("22:50:30"),LocalDate.of(2007,12,9),6,"Mangalore",9865325678L);
        entityManager.persistAndFlush(restaurant);

       	Restaurant fromDb = repository.findById(restaurant.getRestaurantId())
        										.orElse(null);
        assertThat(fromDb.getRestaurantId()).isEqualTo(restaurant.getRestaurantId());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
    	Restaurant fromDb = repository.findById(-11).orElse(null);
        assertThat(fromDb).isNull();
    }

}
