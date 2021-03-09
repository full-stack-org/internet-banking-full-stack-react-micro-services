package com.internet.banking.payments.service.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ScheduledPaymentsBean {
	
	private String fromAccountNumber;

	private String toAccountNumber;

	private String referenceNumber;

	private String paymentDate;
	
	private double paymentAmount;
}
