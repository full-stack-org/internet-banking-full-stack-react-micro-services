package com.internet.banking.routing.number.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.routing.number.request.AddRoutingNumberRequest;
import com.internet.banking.routing.number.request.ValidateRoutingNumberRequest;
import com.internet.banking.routing.number.response.AddRoutingNumberResponse;
import com.internet.banking.routing.number.response.ValidateRoutingNumberResponse;
import com.internet.banking.routing.number.service.RoutingNumberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/routing/v1")
@Slf4j
public class RoutingNumberController {
	
	@Autowired
	private RoutingNumberService routingNumberService;

	/**
	 * addRoutingNumber.
	 * 
	 * @param addRoutingNumberRequest
	 * @return AddRoutingNumberResponse
	 */
	@PostMapping("/addNewRoutingNumber")
	public AddRoutingNumberResponse addRoutingNumber(@Valid @RequestBody AddRoutingNumberRequest addRoutingNumberRequest) {
		log.info("Enter addRoutingNumber of  RoutingNumberController");
		
		AddRoutingNumberResponse addRoutingNumberResponse = routingNumberService.addRoutingNumber(addRoutingNumberRequest);
		
		log.info("Enter addRoutingNumber of  RoutingNumberController");
		
		return addRoutingNumberResponse;
		
	}
	
	/**
	 * validateRoutingNumber.
	 * 
	 * @param validateRoutingNumberRequest
	 * @return ValidateRoutingNumberResponse
	 */
	@PostMapping("/validateRoutingNumber")
	public ValidateRoutingNumberResponse validateRoutingNumber(@Valid @RequestBody ValidateRoutingNumberRequest validateRoutingNumberRequest) {
		log.info("Enter validateRoutingNumber of  RoutingNumberController");
		
		ValidateRoutingNumberResponse validateRoutingNumberResponse = routingNumberService.validateRoutingNumber(validateRoutingNumberRequest);
		
		log.info("Enter validateRoutingNumber of  RoutingNumberController");
		
		return validateRoutingNumberResponse;
		
	}
}
