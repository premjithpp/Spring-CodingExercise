package com.demo.tableReservation.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.tableReservation.dto.SearchAvailability;
import com.demo.tableReservation.entity.ReservationDetails;
import com.demo.tableReservation.entity.RestaurantAvailability;
import com.demo.tableReservation.entity.RestaurantDetails;
import com.demo.tableReservation.repository.ReservationTableRepo;
import com.demo.tableReservation.repository.RestaurantDetailsRepo;
import com.demo.tableReservation.repository.TableAvailablityRepo;


@Service

public class ReservationService {

    @Autowired
    private RestaurantDetailsRepo restaurantDetailsRepo;

    @Autowired
    private ReservationTableRepo reservationTableRepo;

    @Autowired
    private TableAvailablityRepo tableAvailablityRepo;


    /**
     * Save the reservation made to the reservation table
     * Update the restaurant availability table with the date and booked number of seats
     * @param reservationDetails
     * @return boolean
     */
    public boolean bookReservation(ReservationDetails reservationDetails) {
        RestaurantDetails restaurantDetails = restaurantDetailsRepo.findById(reservationDetails.getRestaurantId()).get();

        RestaurantAvailability restaurantAvailability = tableAvailablityRepo.findByRestaurantDetailsRestaurantIdAndAvailableDate(reservationDetails.getRestaurantId(), reservationDetails.getBookingDate());


        reservationDetails.setRestaurantDetails(restaurantDetails);
        if (Objects.isNull(restaurantAvailability)) {
            RestaurantAvailability restaurantAvailabilityData = new RestaurantAvailability();
            restaurantAvailabilityData.setAvailableDate(reservationDetails.getBookingDate());
            restaurantAvailabilityData.setAvailableSeats(restaurantDetails.getSeatingCapacity() - reservationDetails.getBookedTables());
            restaurantAvailabilityData.setRestaurantDetails(restaurantDetails);
            restaurantAvailabilityData.setAvailability(true);
            tableAvailablityRepo.save(restaurantAvailabilityData);
            reservationTableRepo.save(reservationDetails);
            return true;
        } else {
            int remainingSeats = restaurantAvailability.getAvailableSeats() - reservationDetails.getBookedTables();

            if (!restaurantAvailability.getAvailability() || remainingSeats < 0) {
                return false;
            } else {

                restaurantAvailability.setAvailableSeats(remainingSeats);
                if (remainingSeats == 0) {
                    restaurantAvailability.setAvailability(false);
                }
                tableAvailablityRepo.save(restaurantAvailability);
                reservationTableRepo.save(reservationDetails);
                return true;
            }

        }


    }


    /**
     * Update the reservation details
     * checks if there is a reservation with the reservationId to update in reservation table
     * if there is any change in the booking seats updating the same in the availability table
     * @param reservationDetails
     * @return boolean
     */
    public boolean updateReservationDetails(ReservationDetails reservationDetails) {
        Optional<ReservationDetails> existingReservationDetails = reservationTableRepo.findById(reservationDetails.getReservationId());
        if (existingReservationDetails.isPresent() && (reservationDetails.getRestaurantId() == existingReservationDetails.get().getRestaurantDetails().getRestaurantId())) {
            RestaurantAvailability restaurantAvailability = tableAvailablityRepo.findByRestaurantDetailsRestaurantIdAndAvailableDate(reservationDetails.getRestaurantId(), reservationDetails.getBookingDate());
            int tableDifference = reservationDetails.getBookedTables() - existingReservationDetails.get().getBookedTables();
            if (tableDifference != 0) {
                restaurantAvailability.setAvailableSeats(restaurantAvailability.getAvailableSeats() - tableDifference);
                tableAvailablityRepo.save(restaurantAvailability);
            }
            reservationDetails.setRestaurantDetails(existingReservationDetails.get().getRestaurantDetails());
            reservationTableRepo.save(reservationDetails);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Cancel the reservation made if the reservation is already present based on the reservationId
     * @param reservationId
     * @return boolean
     */
    public boolean cancelReservation(Long reservationId) {
        Optional<ReservationDetails> reservationDetails = reservationTableRepo.findById(reservationId);
        if (reservationDetails.isPresent()) {
            RestaurantAvailability restaurantAvailability = tableAvailablityRepo.findByRestaurantDetailsRestaurantIdAndAvailableDate(reservationDetails.get().getRestaurantDetails().getRestaurantId(), reservationDetails.get().getBookingDate());
            restaurantAvailability.setAvailableSeats(reservationDetails.get().getBookedTables() + restaurantAvailability.getAvailableSeats());
            if (!restaurantAvailability.getAvailability()) {
                restaurantAvailability.setAvailability(true);
            }
            reservationTableRepo.deleteById(reservationId);
            return true;
        } else {
            return false;
        }

    }


    /**
     * Find the available restaurants based on search criteria from the restaurant availability table
     * @param searchAvailability
     * @return List<RestaurantAvailability>
     * Return the list of available restaurants
     */
    public List<RestaurantAvailability> findAvailableTables(SearchAvailability searchAvailability) {

        return tableAvailablityRepo.findByAvailableSeatsGreaterThanAndAvailableDateOrAvailableDateBetween(searchAvailability.getSeatCapacity(), searchAvailability.getSearchDate(), searchAvailability.getStartDate(), searchAvailability.getEndDate());


    }


}
