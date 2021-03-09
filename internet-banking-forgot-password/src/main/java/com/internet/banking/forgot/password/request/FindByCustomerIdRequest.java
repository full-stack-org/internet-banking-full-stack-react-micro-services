package com.internet.banking.forgot.password.request;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindByCustomerIdRequest {

	@Min(value = 0, message = "The value must be positive")
	private int customerId;

}
