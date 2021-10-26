package com.demo.tableReservation.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Class to map the parameters for finding the availability of restaurants
 * @author Premjith
 */
public class SearchAvailability {


    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date startDate;
    private int seatCapacity;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date endDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date searchDate;


    public SearchAvailability() {
    }

    public SearchAvailability(Date startDate, int seatCapacity, Date endDate, Date searchDate) {
        super();
        this.startDate = startDate;
        this.seatCapacity = seatCapacity;
        this.endDate = endDate;
        this.searchDate = searchDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }


}
