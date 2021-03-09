package com.internet.banking.account.open.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.account.open.request.AccountOpenRequest;
import com.internet.banking.account.open.response.AccountOpenResponse;
import com.internet.banking.account.open.service.AccountOpenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/banking/v1")
@Slf4j
public class AccountOpeningController {

	@Autowired
	private AccountOpenService accountOpenService;

	/**
	 * openNewAccount.
	 * 
	 * @param accountOpenRequest
	 * @return AccountOpenResponse
	 */
	@PostMapping("/openNewAccount")
	public AccountOpenResponse openNewAccount(@Valid @RequestBody AccountOpenRequest accountOpenRequest) {
		log.info("Enter in openNewAccount of AccountOpeningController");

		AccountOpenResponse accountOpenResponse = accountOpenService.openNewAccount(accountOpenRequest);

		log.info("Exit in openNewAccount of AccountOpeningController");

		return accountOpenResponse;
	}
}
