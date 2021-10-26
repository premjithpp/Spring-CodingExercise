package com.demo.tableReservation.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Table entity to store the details of all the restaurants
 *
 * @author premjith
 */
@Entity
@Table(name = "RestaurantDetails")
public class RestaurantDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RestaurantId")
    private Long restaurantId;

    @Column(name = "RestaurantName")
    private String restaurantName;

    @Column(name = "SeatingCapacity")
    private int seatingCapacity;


    public RestaurantDetails() {
        super();
    }

    public RestaurantDetails(Long restaurantId, String restaurantName, int seatingCapacity) {
        super();
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.seatingCapacity = seatingCapacity;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }


}
