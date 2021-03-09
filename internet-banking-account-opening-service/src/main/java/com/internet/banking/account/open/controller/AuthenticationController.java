package com.internet.banking.account.open.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.account.open.request.AuthenticationRequest;
import com.internet.banking.account.open.response.AuthenticationResponse;
import com.internet.banking.account.open.service.AccountOpenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/banking/login/v1")
@Slf4j
public class AuthenticationController {

	@Autowired
	private AccountOpenService accountOpenService;

	/**
	 * authenticate.
	 * 
	 * @param authenticationRequest
	 * @return AuthenticationResponse
	 */
	@PostMapping("/authenticate")
	public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
		log.info("Enter in authenticate of AuthenticationController");

		AuthenticationResponse authenticationResponse = accountOpenService.authenticate(authenticationRequest);

		log.info("Exit in authenticate of AuthenticationController");

		return authenticationResponse;
	}
}
