package com.demo.tableReservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.tableReservation.entity.RestaurantDetails;
@Repository
public interface RestaurantDetailsRepo extends JpaRepository<RestaurantDetails, Long>{
	
	public List<RestaurantDetails> findBySeatingCapacityLessThanEqual(int tableNumber);

	

}
