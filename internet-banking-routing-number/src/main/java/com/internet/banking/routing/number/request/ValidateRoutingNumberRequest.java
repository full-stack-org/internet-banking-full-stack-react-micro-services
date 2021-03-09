package com.internet.banking.routing.number.request;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateRoutingNumberRequest {
	
	@NotEmpty(message = "Routing Number is Required")
	private String routingNumber;
}
