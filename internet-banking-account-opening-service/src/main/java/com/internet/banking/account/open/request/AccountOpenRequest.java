package com.internet.banking.account.open.request;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOpenRequest {

	@NotEmpty(message = "First Name is Mandatory")
	private String firstName;

	@NotEmpty(message = "Last Name is Mandatory")
	private String lastName;

	@NotEmpty(message = "Gender is Mandatory")
	private String gender;

	@NotEmpty(message = "Date of Birth is Mandatory")
	private String dateOfBirth;

	@NotEmpty(message = "Aadhar Number is Mandatory")
	private String aadharNumber;

	@NotEmpty(message = "Pan Number is Mandatory")
	private String panNumber;

	@NotEmpty(message = "Account Type is Mandatory")
	private String accountType;
	
	private String password;

}
