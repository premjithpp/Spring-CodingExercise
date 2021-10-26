package com.demo.tableReservation.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.tableReservation.dto.Payload;
import com.demo.tableReservation.dto.SearchAvailability;
import com.demo.tableReservation.entity.ReservationDetails;
import com.demo.tableReservation.entity.RestaurantAvailability;
import com.demo.tableReservation.entity.RestaurantDetails;
import com.demo.tableReservation.service.ReservationService;
import com.demo.tableReservation.service.RestaurantService;


/**
 * Controller for mapping the paths to perform restaurant reservation services
 */
@RestController
@RequestMapping("restaurantService")
public class ReservationController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReservationService reservationService;


    /**
     * Get the list of all the restaurants
     *
     * @return Payload<List < RestaurantDetails>>
     * returns list of RestaurantDetails
     */
    @GetMapping("getAllRestaurants")
    private Payload<List<RestaurantDetails>> getAllRestaurants() {

        return new Payload<List<RestaurantDetails>>().withData(restaurantService.getAllRestauarants()).withMsg("Successfully Retrived All Restaurants");

    }


    /**
     * Get the Detail of a restaurant by id
     *
     * @param restaurantId
     * @return ResponseEntity<Payload < RestaurantDetails>>
     * returns the details of the restaurant with the passed id
     */
    @GetMapping("getRestaurant/{id}")
    private ResponseEntity<Payload<RestaurantDetails>> getRestaurantById(@PathVariable(value = "id") Long restaurantId) {

        RestaurantDetails restaurantDetails = restaurantService.getRestaurantById(restaurantId);
        if (Objects.isNull(restaurantDetails)) {
            return new ResponseEntity<Payload<RestaurantDetails>>(new Payload<RestaurantDetails>().withMsg("Restaurant not present"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Payload<RestaurantDetails>>(new Payload<RestaurantDetails>().withData(restaurantDetails).withMsg("Successfully retrieved restaurant details"), HttpStatus.OK);
        }

    }


    /**
     * Book a reservation
     *
     * @param reservationDetails
     * @return ResponseEntity<Payload < Boolean>>
     * Returns if booking is successful or not
     */
    @PostMapping("/bookReservation")
    private ResponseEntity<Payload<Boolean>> bookRestaurant(@Valid @RequestBody ReservationDetails reservationDetails) {
        boolean status = reservationService.bookReservation(reservationDetails);
        if (status) {
            return new ResponseEntity<>(new Payload<Boolean>().withData(true).withMsg("Reservation Successfull"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Payload<Boolean>().withData(false).withMsg("Failed to reserve the table"), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Update a reservation
     *
     * @param reservationDetails
     * @return ResponseEntity<Payload < Boolean>>
     * returns if the update is successful of not
     */
    @PutMapping("/updateReservation")
    private ResponseEntity<Payload<Boolean>> updateBookingDetails(@Valid @RequestBody ReservationDetails reservationDetails) {
        boolean status = reservationService.updateReservationDetails(reservationDetails);
        if (status) {
            return new ResponseEntity<>(new Payload<Boolean>().withData(true).withMsg("Details Updated Successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Payload<Boolean>().withData(false).withMsg("Reservation not Found for the restaurant"), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Cancel a reservation
     *
     * @param reservationId
     * @return ResponseEntity<Payload < Boolean>>
     * returns if the deletion is successful or not
     */
    @DeleteMapping("/deleteReservation/{id}")
    private ResponseEntity<Payload<Boolean>> cancelReservation(@PathVariable("id") Long reservationId) {
        boolean status = reservationService.cancelReservation(reservationId);
        if (status) {
            return new ResponseEntity<>(new Payload<Boolean>().withData(true).withMsg("Reservation cancelled Successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Payload<Boolean>().withData(false).withMsg("Reservation not Found"), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Find the availability of the restaurants based on available seats and date
     *
     * @param searchAvailability
     * @return payload<List < RestaurantAvailability>>
     * returns list of available restaurants
     */
    @PostMapping("findAvailability")
    private Payload<List<RestaurantAvailability>> findAvailability(@RequestBody SearchAvailability searchAvailability) {
        return new Payload<List<RestaurantAvailability>>().withData(reservationService.findAvailableTables(searchAvailability)).withMsg("Successfully retrieved Details");

    }


}
