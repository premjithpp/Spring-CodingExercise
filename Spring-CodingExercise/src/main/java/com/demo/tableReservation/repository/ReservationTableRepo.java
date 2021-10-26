package com.demo.tableReservation.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.tableReservation.entity.ReservationDetails;

public interface ReservationTableRepo extends JpaRepository<ReservationDetails, Long> {
	

}
