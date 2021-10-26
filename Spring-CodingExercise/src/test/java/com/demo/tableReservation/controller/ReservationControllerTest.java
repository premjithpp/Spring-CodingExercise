package com.demo.tableReservation.controller;



import com.demo.tableReservation.dto.SearchAvailability;
import com.demo.tableReservation.entity.ReservationDetails;
import com.demo.tableReservation.entity.RestaurantAvailability;
import com.demo.tableReservation.entity.RestaurantDetails;
import com.demo.tableReservation.service.ReservationService;
import com.demo.tableReservation.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @Mock
     RestaurantService restaurantService;



    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

    }


    /**
     * Test for GetAllRestaurants controller
     * @throws Exception
     */
    @Test
    public void getAllRestaurantsTest() throws Exception {
        RestaurantDetails restaurantDetails1 = new RestaurantDetails(1L, "Restaurant1", 20);
        RestaurantDetails restaurantDetails2 = new RestaurantDetails(1L, "Restaurant1", 20);

        List<RestaurantDetails> restaurantDetailsList = new ArrayList<>();
        restaurantDetailsList.add(restaurantDetails1);
        restaurantDetailsList.add(restaurantDetails2);

        Mockito.when(restaurantService.getAllRestauarants()).thenReturn(restaurantDetailsList);
        String url="/restaurantService/getAllRestaurants";
        mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk());

    }


    /**
     * Test get Restaurant by id controller Success
     * @throws Exception
     */
    @Test
    public void getRestaurantById_SuccessTest() throws Exception {
        RestaurantDetails restaurantDetails1 = new RestaurantDetails(1L, "Restaurant1", 20);
        Mockito.when(restaurantService.getRestaurantById(anyLong())).thenReturn(restaurantDetails1);
        String url="/restaurantService/getRestaurant/{id}";
        mockMvc.perform(MockMvcRequestBuilders.get(url,1)).andExpect(status().isOk());
        Mockito.when(restaurantService.getRestaurantById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get(url,1)).andExpect(status().isBadRequest());
    }

    /**
     * Test get Restaurant by id controller Failure
     * @throws Exception
     */
    @Test
    public void getRestaurantById_FailureTest() throws Exception {
        String url="/restaurantService/getRestaurant/{id}";
        Mockito.when(restaurantService.getRestaurantById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get(url,1)).andExpect(status().isBadRequest());
    }


    /**
     * Test for booking Reservation controller Success
     * @throws Exception
     */
    @Test
    public void bookReservation_SuccessTest() throws Exception {

        Date sampleDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2021");
        ReservationDetails reservationDetails = new ReservationDetails(1L, sampleDate, "Test", "Test", null, 5);
        reservationDetails.setRestaurantId(1L);
        ObjectWriter objectWritter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String reqObject = objectWritter.writeValueAsString(reservationDetails);
        String url = "/restaurantService/bookReservation";
        Mockito.when(reservationService.bookReservation(any())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(reqObject)).andExpect(status().isOk());

    }

    /**
     * Test for booking Reservation controller Failure
     * @throws Exception
     */
    @Test
    public void bookReservation_FailureTest() throws Exception {

        Date sampleDate=new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2021");
        ReservationDetails reservationDetails = new ReservationDetails(1L, sampleDate, "Test", "Test", null, 5);
        reservationDetails.setRestaurantId(1L);
        ObjectWriter objectWritter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String reqObject=  objectWritter.writeValueAsString(reservationDetails);
        String url="/restaurantService/bookReservation";
        Mockito.when(reservationService.bookReservation(any())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(reqObject)).andExpect(status().isBadRequest());
    }


    /**
     * Test for update reservation controller
     * @throws Exception
     */
    @Test
    public void updateBookingDetailsTest() throws Exception {
        Date sampleDate=new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2021");

        ReservationDetails reservationDetails = new ReservationDetails(1L, sampleDate, "Test", "Test", null, 5);
        reservationDetails.setRestaurantId(1L);

        ObjectWriter objectWritter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String reqObject=  objectWritter.writeValueAsString(reservationDetails);

        String url="/restaurantService/updateReservation";

        /* Testing when reservation is successful */

        Mockito.when(reservationService.updateReservationDetails(any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON).content(reqObject)).andExpect(status().isOk());

        /* Testing when reservation is Failed */
        Mockito.when(reservationService.updateReservationDetails(any())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON).content(reqObject)).andExpect(status().isBadRequest());
    }


    /**
     * Test for delete reservation controller
     * @throws Exception
     */
    @Test
    public void deleteReservation_SuccessTest() throws Exception {

        Mockito.when(reservationService.cancelReservation(anyLong())).thenReturn(true);
        String url="/restaurantService/deleteReservation/{id}";
        mockMvc.perform(MockMvcRequestBuilders.delete(url,1)).andExpect(status().isOk());

    }


    /**
     * Test for delete reservation controller
     * @throws Exception
     */
    @Test
    public void deleteReservation_FailureTest() throws Exception {

        String url="/restaurantService/deleteReservation/{id}";
        Mockito.when(reservationService.cancelReservation(anyLong())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete(url,1)).andExpect(status().isBadRequest());

    }




    @Test
    public void findAvailabilityTest() throws Exception {

        RestaurantAvailability restaurantAvailability = new RestaurantAvailability(1L, true,10,null);
        List<RestaurantAvailability> restaurantAvailabilityList = new ArrayList<>();
        restaurantAvailabilityList.add(restaurantAvailability);
        Mockito.when(reservationService.findAvailableTables(any())).thenReturn(restaurantAvailabilityList);
        String url="/restaurantService/findAvailability";


        SearchAvailability searchAvailability = new SearchAvailability(null, 1, null, null);

        ObjectWriter objectWritter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String reqObject=  objectWritter.writeValueAsString(searchAvailability);

        mockMvc.perform(MockMvcRequestBuilders.post(url).content(reqObject).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }
}
