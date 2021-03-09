package com.internet.banking.login.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
	@Min(value = 0, message = "The value must be positive")
	private int customerId;

	@NotEmpty(message = "Password is Required")
	private String password;
}
