package com.demo.tableReservation.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.demo.tableReservation.dto.Payload;



@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Payload<String>> handleException(RuntimeException e) {
		return new ResponseEntity<>(new Payload<String>().withMsg("Error occured while processing request").withData(e.getLocalizedMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}