package com.demo.tableReservation.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;


import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @author Premjith
 * Table Entity to store details of the reservation
 */
@Entity
@Table(name = "ReservationDetails")
public class ReservationDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ReservationId")
    private Long reservationId;

    @Column(name = "BookingDate")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @NotNull(message = "Booking Date is mandatory")
    private Date bookingDate;

    @Column(name = "BookingTime")
    private String bookingTime;

    @Column(name = "BookingUserName")
    @NotEmpty(message = "Name is mandatory")
    private String bookingUserName;


    @Column(name = "BookedTables")
    private int bookedTables;

    @Column(name = "CreatedDate")
    @CreationTimestamp
    private LocalDateTime bookingTimeStamp;


    @Transient
    private Long restaurantId;


    @ManyToOne
    @JoinColumn(name = "RestaurantId")
    private RestaurantDetails restaurantDetails;


    public ReservationDetails() {
        super();
        // TODO Auto-generated constructor stub
    }


    public ReservationDetails(Long reservationId, Date bookingDate,
                              String bookingTime, String bookingUserName, LocalDateTime bookingTimeStamp, int bookedTables) {
        super();
        this.reservationId = reservationId;

        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingUserName = bookingUserName;

        this.bookingTimeStamp = bookingTimeStamp;
        this.bookedTables = bookedTables;
    }


    public int getBookedTables() {
        return bookedTables;
    }


    public void setBookedTables(int bookedTables) {
        this.bookedTables = bookedTables;
    }


    public Long getReservationId() {
        return reservationId;
    }


    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }


    public Date getBookingDate() {
        return bookingDate;
    }


    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }


    public String getBookingTime() {
        return bookingTime;
    }


    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }


    public String getBookingUserName() {
        return bookingUserName;
    }


    public void setBookingUserName(String bookingUserName) {
        this.bookingUserName = bookingUserName;
    }


    public LocalDateTime getBookingTimeStamp() {
        return bookingTimeStamp;
    }


    public void setBookingTimeStamp(LocalDateTime bookingTimeStamp) {
        this.bookingTimeStamp = bookingTimeStamp;
    }


    public Long getRestaurantId() {
        return restaurantId;
    }


    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }


    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }


    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }


}
