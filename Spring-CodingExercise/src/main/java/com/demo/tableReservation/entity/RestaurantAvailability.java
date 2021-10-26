package com.demo.tableReservation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Table entity to store and update the availability of the restaurant for particular dates
 *
 * @author Premjith
 */
@Entity
@Table(name = "RestaurantAvailability")
public class RestaurantAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AvailabilityId")
    @JsonIgnore
    private Long availabilityId;

    @Column(name = "Availability")
    private boolean availability;

    @Column(name = "AvailableSeats")
    private int availableSeats;


    @Column(name = "AvailableDate")
    private Date availableDate;

    @ManyToOne
    @JoinColumn(name = "RestaurantId", referencedColumnName = "restaurantId")
    private RestaurantDetails restaurantDetails;


    public RestaurantAvailability() {
        super();
        // TODO Auto-generated constructor stub
    }


    public RestaurantAvailability(Long availablityId, boolean availablity, int availableSeats, Date availableDate) {
        super();
        this.availabilityId = availablityId;
        this.availability = availablity;
        this.availableSeats = availableSeats;
        this.availableDate = availableDate;
    }


    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }


    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }


}
