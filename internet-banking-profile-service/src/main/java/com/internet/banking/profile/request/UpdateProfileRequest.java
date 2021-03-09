package com.internet.banking.profile.request;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
	
	@Min(value = 0, message = "The value must be positive")
	private Long id;

	private String firstName;

	private String lastName;

	private String gender;

	private String dateOfBirth;

	private String aadharNumber;

	private String panNumber;


}
