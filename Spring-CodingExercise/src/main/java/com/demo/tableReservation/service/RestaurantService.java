package com.demo.tableReservation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.tableReservation.entity.RestaurantDetails;
import com.demo.tableReservation.repository.RestaurantDetailsRepo;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDetailsRepo restaurantDetailsRepo;


    /**
     * Find the list of details of restaurants from restaurant table
     * @return All restaurant details
     */
    public List<RestaurantDetails> getAllRestauarants() {
        return restaurantDetailsRepo.findAll();

    }


    /**
     * Find the detail of a restaurant from restaurant table based on restaurantId
     * @param restaurantId
     * @return List of restaurants filtered by id
     */
    public RestaurantDetails getRestaurantById(Long restaurantId) {
        Optional<RestaurantDetails> restaurantDetails = restaurantDetailsRepo.findById(restaurantId);
        if (restaurantDetails.isPresent()) {
            return restaurantDetails.get();
        } else {
            return null;
        }


    }


}
