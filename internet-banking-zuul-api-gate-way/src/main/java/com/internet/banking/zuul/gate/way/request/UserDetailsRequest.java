package com.internet.banking.zuul.gate.way.request;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRequest {
	@Min(value = 0, message = "The value must be positive")
	private int customerId;
}
