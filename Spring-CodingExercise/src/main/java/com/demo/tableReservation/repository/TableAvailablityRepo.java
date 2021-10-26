package com.demo.tableReservation.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.tableReservation.entity.RestaurantAvailability;

@Repository
public interface TableAvailablityRepo extends JpaRepository<RestaurantAvailability,Long> {
	
	RestaurantAvailability findByRestaurantDetailsRestaurantIdAndAvailableDate(Long id, Date availableDate);
	
	List<RestaurantAvailability> findByAvailableSeatsGreaterThanAndAvailableDateOrAvailableDateBetween(int requiredSeats, Date availableDate, Date startDate, Date endDate);
	
	

}
