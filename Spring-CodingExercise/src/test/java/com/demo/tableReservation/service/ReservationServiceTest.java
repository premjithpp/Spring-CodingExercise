package com.demo.tableReservation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.demo.tableReservation.dto.SearchAvailability;
import com.demo.tableReservation.entity.ReservationDetails;
import com.demo.tableReservation.entity.RestaurantAvailability;
import com.demo.tableReservation.entity.RestaurantDetails;
import com.demo.tableReservation.repository.ReservationTableRepo;
import com.demo.tableReservation.repository.RestaurantDetailsRepo;
import com.demo.tableReservation.repository.TableAvailablityRepo;

import static org.mockito.ArgumentMatchers.*;

/**
 *  Class with test cases for Reservation Service
 */
@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
	
	@InjectMocks
	private ReservationService reservationService;
	
	@Mock
	private RestaurantDetailsRepo restaurantDetailsRepo;
	
	@Mock
	private ReservationTableRepo reservationTableRepo;
	
	@Mock
	private TableAvailablityRepo tableAvailablityRepo;
	

	
	


	/**
	 * Test for creating reservation when availability is missing in availability table
	 *
	 */
	@Test
	public void bookReservation_SuccessTest(){
		Optional<RestaurantDetails> restaurantDetails = Optional.of(new RestaurantDetails(1L, "Test", 10));

		when(restaurantDetailsRepo.findById(anyLong())).thenReturn(restaurantDetails);

		ReservationDetails reservationDetails = new ReservationDetails(1L, null, "Test", "Test", null, 5);
		reservationDetails.setRestaurantId(1L);

		assertTrue(reservationService.bookReservation(reservationDetails));


	}

	/**
	 * Test for creating reservation after checking the availability and number of available seats
	 * Success scenario
	 */
	@Test
	public void bookReservationAvailability_SuccessTest() throws ParseException {
		Date sampleDate=new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2021");
		Optional<RestaurantDetails> restaurantDetails = Optional.of(new RestaurantDetails(1L, "Test", 10));
		RestaurantAvailability restaurantAvailability = new RestaurantAvailability(1L, true,10,null);
		restaurantAvailability.setRestaurantDetails(restaurantDetails.get());

		when(restaurantDetailsRepo.findById(anyLong())).thenReturn(restaurantDetails);
		when(tableAvailablityRepo.findByRestaurantDetailsRestaurantIdAndAvailableDate(anyLong(),any(Date.class))).thenReturn(restaurantAvailability);

		ReservationDetails reservationDetails = new ReservationDetails(1L, sampleDate, "Test", "Test", null, 5);
		reservationDetails.setRestaurantId(1L);

		assertTrue(reservationService.bookReservation(reservationDetails));



	}

	/**
	 * Test for creating reservation after checking the avilability and number of available seats
	 * Failure Scenario
	 */
	@Test
	public void bookReservation_FailureTest() throws ParseException {
		Date sampleDate=new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2021");
		Optional<RestaurantDetails> restaurantDetails = Optional.of(new RestaurantDetails(1L, "Test", 10));
		RestaurantAvailability restaurantAvailability = new RestaurantAvailability(1L, true,10,null);
		restaurantAvailability.setRestaurantDetails(restaurantDetails.get());

		when(restaurantDetailsRepo.findById(anyLong())).thenReturn(restaurantDetails);
		when(tableAvailablityRepo.findByRestaurantDetailsRestaurantIdAndAvailableDate(anyLong(),any(Date.class))).thenReturn(restaurantAvailability);

		ReservationDetails reservationDetails = new ReservationDetails(1L, sampleDate, "Test", "Test", null, 20);
		reservationDetails.setRestaurantId(1L);
		assertFalse(reservationService.bookReservation(reservationDetails));


	}
	
	/**
	 * Test for finding available table based on criteria
	 *
	 */
	@Test
	public void findAvailableTablesTest() throws ParseException {
		
		Date sampleDate=new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2021");
		RestaurantAvailability restaurantAvailability1 = new RestaurantAvailability(1L, false,10,sampleDate);
		RestaurantAvailability restaurantAvailability2 = new RestaurantAvailability(2L,false,10,sampleDate);
		RestaurantDetails restaurantDetails = new RestaurantDetails(1L, "Test", 10);
		restaurantAvailability1.setRestaurantDetails(restaurantDetails);
		restaurantAvailability2.setRestaurantDetails(restaurantDetails);
		
		List<RestaurantAvailability> restaurantAvailabilityList = new ArrayList<>();
		restaurantAvailabilityList.add(restaurantAvailability2);
		restaurantAvailabilityList.add(restaurantAvailability1);
		
		when(tableAvailablityRepo.findByAvailableSeatsGreaterThanAndAvailableDateOrAvailableDateBetween(anyInt(), any(Date.class), any(Date.class), any(Date.class))).thenReturn(restaurantAvailabilityList);
		
		SearchAvailability searchAvailability = new SearchAvailability(sampleDate, 1, sampleDate, sampleDate);
		assertEquals(reservationService.findAvailableTables(searchAvailability).size(), 2);
	}
	
	
	/**
	 * Cancel the Reservation when the reservation id is present
	 */
	@Test
	public void cancelReservation_SuccessTest() throws ParseException {
		Date sampleDate=new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2021");
		 RestaurantDetails restaurantDetails = new RestaurantDetails(1L, "Test", 20);
		
		ReservationDetails reservationDetails = new ReservationDetails(1L, sampleDate, "Test", "Test", null, 10);
		reservationDetails.setRestaurantDetails(restaurantDetails);
		when(reservationTableRepo.findById(anyLong())).thenReturn(Optional.of(reservationDetails));
		
	    RestaurantAvailability restaurantAvailability = new RestaurantAvailability(1L, true, 10, sampleDate);
	   
	    restaurantAvailability.setRestaurantDetails(restaurantDetails);
        when(tableAvailablityRepo.findByRestaurantDetailsRestaurantIdAndAvailableDate(anyLong(), any(Date.class))).thenReturn(restaurantAvailability);


		assertTrue(reservationService.cancelReservation(1L));

		restaurantAvailability.setAvailability(false);
		when(tableAvailablityRepo.findByRestaurantDetailsRestaurantIdAndAvailableDate(anyLong(), any(Date.class))).thenReturn(restaurantAvailability);
		assertTrue(reservationService.cancelReservation(1L));

	}
	
	/**
	 * Cancel the Reservation when the reservation id is not present
	 * 
	 */
	@Test
	public void cancelReservation_FailureTest() {
		
		when(reservationTableRepo.findById(anyLong())).thenReturn(Optional.empty());

		assertFalse(reservationService.cancelReservation(1L));
	}


	@Test
	public void updateReservation_SuccessTest() {

		ReservationDetails existingReservation = new ReservationDetails(1L, null, "Test", "Test", null, 10);
		RestaurantDetails restaurantDetails = new RestaurantDetails(1L, "Test", 20);
		existingReservation.setRestaurantDetails(restaurantDetails);
		ReservationDetails newReservation = new ReservationDetails(1L, null, "Test", "Test", null, 10);
		newReservation.setRestaurantId(1L);

		when(reservationTableRepo.findById(anyLong())).thenReturn(Optional.of(existingReservation));
		assertTrue(reservationService.updateReservationDetails(newReservation));


	}


	@Test
	public void updateReservation_FailureTest()  {
		when(reservationTableRepo.findById(anyLong())).thenReturn(Optional.empty());
		ReservationDetails newReservation = new ReservationDetails(1L, null, "Test", "Test", null, 10);
		assertFalse(reservationService.updateReservationDetails(newReservation));


	}







}
