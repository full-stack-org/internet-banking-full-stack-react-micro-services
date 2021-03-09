package com.internet.banking.account.open.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CustomerProfileResponse {
	private Long id;

	private String firstName;

	private String lastName;

	private String aadharNumber;

	private String panNumber;

	private String dateOfBirth;

	private String gender;

	private int customerId;
	
	private StatusResponse statusResponse;
}
