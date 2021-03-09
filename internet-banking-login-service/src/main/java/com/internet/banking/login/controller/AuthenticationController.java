package com.internet.banking.login.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.login.jwt.JWTTokenGenerator;
import com.internet.banking.login.proxy.AuthenticationProxy;
import com.internet.banking.login.request.AuthenticationRequest;
import com.internet.banking.login.response.AuthenticationResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/login/v1")
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationProxy authenticationProxy;

	@Autowired
	private JWTTokenGenerator jwtTokenGenerator;

	/**
	 * authenticate.
	 * 
	 * @param authenticationRequest
	 * @return AuthenticationResponse
	 */
	@PostMapping("/authenticate")
	public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
		log.info("Enter in authenticate of AuthenticationController");

		AuthenticationResponse authenticationResponse = authenticationProxy.authenticate(authenticationRequest);

		if (Objects.nonNull(authenticationResponse)
				&& authenticationResponse.getStatusResponse().getStatusCode() == 200) {
			
			String jwtToken = jwtTokenGenerator.generateToken(String.valueOf(authenticationRequest.getCustomerId()));

			authenticationResponse.setJwtToken(jwtToken);
		}

		log.info("Exit in authenticate of AuthenticationController");

		return authenticationResponse;
	}

}
