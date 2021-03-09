package com.internet.banking.external.account.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.external.account.request.ExternalAccountRequest;
import com.internet.banking.external.account.request.GetAllExternalAccountsRequest;
import com.internet.banking.external.account.response.ExternalAccountResponse;
import com.internet.banking.external.account.response.GetAllAccountsResponse;
import com.internet.banking.external.account.service.ExternalAccountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/external/account/v1")
@Slf4j
public class ExternalAccountController {

	@Autowired
	private ExternalAccountService externalAccountService;

	/**
	 * addExternalAccount.
	 * 
	 * @param externalAccountRequest
	 * @return ExternalAccountResponse
	 */
	@PostMapping("/addExternalAccount")
	public ExternalAccountResponse addExternalAccount(
			@Valid @RequestBody ExternalAccountRequest externalAccountRequest) {
		log.info("Enter in addExternalAccount of ExternalAccountController");

		ExternalAccountResponse externalAccountResponse = externalAccountService
				.addExternalAccount(externalAccountRequest);

		log.info("Exit in addExternalAccount of ExternalAccountController");

		return externalAccountResponse;

	}

	/**
	 * getAllExternalAccounts.
	 * 
	 * @param externalAccountRequest
	 * @return GetAllAccountsResponse
	 */
	@PostMapping("/getAllExternalAccounts")
	public GetAllAccountsResponse getAllExternalAccounts(
			@Valid @RequestBody GetAllExternalAccountsRequest externalAccountRequest) {
		log.info("Enter in addExternalAccount of ExternalAccountController");

		GetAllAccountsResponse externalAccountResponse = externalAccountService
				.getAllExternalAccounts(externalAccountRequest);

		log.info("Exit in addExternalAccount of ExternalAccountController");

		return externalAccountResponse;

	}
}
