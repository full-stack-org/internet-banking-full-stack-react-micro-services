package com.internet.banking.external.account.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalAccountRequest {

	@Min(value = 0, message = "The value must be positive")
	private int customerId;

	@NotEmpty(message = "Accout Number is Mandatory")
	private String accountNumber;
	
	@NotEmpty(message = "Accout Nick Name is Mandatory")
	private String accountNickName;
	
	@NotEmpty(message = "Accout Type is Mandatory")
	private String accountType;
}
