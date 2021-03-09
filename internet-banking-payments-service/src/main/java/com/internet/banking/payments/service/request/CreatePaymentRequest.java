package com.internet.banking.payments.service.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

	@NotEmpty(message = "From Accout Nummber is Mandatory")
	private String fromAccount;

	@NotEmpty(message = "To Accout Nummber is Mandatory")
	private String toAccount;

	@NotEmpty(message = "Payment Amount is Mandatory")
	private String paymentDate;

	@Min(value = 0, message = "The value must be positive")
	private double paymentAmount;

	@Min(value = 0, message = "The value must be positive")
	private int customerId;
}
