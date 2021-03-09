package com.internet.banking.login.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AuthenticationResponse {

	private boolean authenticatedSuccessfully;
	private int customerId;
	
	private String jwtToken;
	
	private StatusResponse statusResponse;
}
