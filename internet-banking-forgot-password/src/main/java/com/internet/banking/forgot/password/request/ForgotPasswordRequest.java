package com.internet.banking.forgot.password.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
	@Min(value = 0, message = "The value must be positive")
	private int customerId;

	@NotEmpty(message = "Password is Mandatory")
	private String password;
}
