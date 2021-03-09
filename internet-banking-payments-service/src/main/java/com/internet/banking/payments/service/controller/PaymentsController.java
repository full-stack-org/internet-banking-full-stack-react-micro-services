package com.internet.banking.payments.service.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.payments.service.request.CreatePaymentRequest;
import com.internet.banking.payments.service.request.ScheduledPaymentsRequest;
import com.internet.banking.payments.service.response.CreatePaymentResponse;
import com.internet.banking.payments.service.response.ScheduledPaymentsResponse;
import com.internet.banking.payments.service.service.PaymentsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/payment/v1")
@Slf4j
public class PaymentsController {

	@Autowired
	private PaymentsService paymentsService;

	/**
	 * createPayment.
	 * 
	 * @param createPaymentRequest
	 * @return CreatePaymentResponse
	 */
	@PostMapping("/createPayment")
	public CreatePaymentResponse createPayment(@Valid @RequestBody CreatePaymentRequest createPaymentRequest) {
		log.info("Enter in createPayment of PaymentsController");

		CreatePaymentResponse createPaymentResponse = paymentsService.createPayment(createPaymentRequest);

		log.info("Exit in createPayment of PaymentsController");

		return createPaymentResponse;

	}

	/**
	 * getAllScheduledPayments.
	 * 
	 * @param scheduledPaymentsRequest
	 * @return ScheduledPaymentsResponse
	 */
	@PostMapping("/getAllScheduledPayments")
	public ScheduledPaymentsResponse getAllScheduledPayments(
			@Valid @RequestBody ScheduledPaymentsRequest scheduledPaymentsRequest) {
		log.info("Enter in getAllScheduledPayments of PaymentsController");

		ScheduledPaymentsResponse scheduledPaymentsResponse = paymentsService
				.getScheduledPayments(scheduledPaymentsRequest);

		log.info("Exit in getAllScheduledPayments of PaymentsController");

		return scheduledPaymentsResponse;

	}

}
