package com.demo.tableReservation.service;

import com.demo.tableReservation.entity.RestaurantDetails;
import com.demo.tableReservation.repository.RestaurantDetailsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * Test cases for Restaurant Services
 * @author premlnu
 *
 */

public class RestaurantServiceTest {
	
	
	@InjectMocks
	private RestaurantService restaurantService;
	
	/* Mock the Restaurant Detail Repo*/
	@Mock
	private RestaurantDetailsRepo restaurantDetailsRepo;
	
	@BeforeEach
	    public void init() {
		  MockitoAnnotations.openMocks(this);

	  }
	  
	List<RestaurantDetails> restaurantList= new ArrayList<>();

	
	/**
	 * Test get all restaurant details
	 */
	@Test
	public void getAllRestauarantsTest() {
		
	  this.initializeData();
	  Mockito.when(restaurantDetailsRepo.findAll()).thenReturn(restaurantList);
	  assertEquals(restaurantService.getAllRestauarants().size(), 2);
	  
	   
	}
	
	/**
	 * get restaurant by id
	 */
	@Test
	public void getAllRestaurantById_SuccessTest() {
		
	  Optional<RestaurantDetails> restaurantTwo = Optional.of(new RestaurantDetails(null, "Test", 10));
	  Mockito.when(restaurantDetailsRepo.findById(anyLong())).thenReturn(restaurantTwo);
	  assertEquals(restaurantService.getRestaurantById(1L), restaurantTwo.get());
	 
	   
	}
	
	/**
	 * Test get restaurant by id when data is not present
	 */
	@Test
	public void getRestaurantById_FailureTest() {
	  
	  Optional<RestaurantDetails> restaurantTwo = Optional.empty();
	  Mockito.when(restaurantDetailsRepo.findById(anyLong())).thenReturn(restaurantTwo);
	  assertNull(restaurantService.getRestaurantById(1L));
	   
	}
	
	 void initializeData() {
		 RestaurantDetails restaurantOne = new RestaurantDetails(1L, "Test", 10);
		  RestaurantDetails restaurantTwo = new RestaurantDetails(1L, "Test", 10);
		  restaurantList.add(restaurantOne);
		  restaurantList.add(restaurantTwo);
	}

}
