package com.internet.banking.internal.bank.accounts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.internal.bank.accounts.proxy.InternalAccountsProxy;
import com.internet.banking.internal.bank.accounts.request.AccountOpenRequest;
import com.internet.banking.internal.bank.accounts.request.InternalAccountListRequest;
import com.internet.banking.internal.bank.accounts.response.AccountOpenResponse;
import com.internet.banking.internal.bank.accounts.response.InternalAccountListResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/internal/accounts/v1")
@Slf4j
public class InternalAccountsController {

	@Autowired
	private InternalAccountsProxy internalAccountsProxy;

	/**
	 * openNewAccount.
	 * 
	 * @param accountOpenRequest
	 * @return AccountOpenResponse
	 */
	@PostMapping("/openAccount")
	public AccountOpenResponse openNewAccount(@Valid @RequestBody AccountOpenRequest accountOpenRequest) {
		log.info("Enter in openNewAccount of InternalAccountsController");

		AccountOpenResponse accountOpenResponse = internalAccountsProxy.openNewAccount(accountOpenRequest);

		log.info("Exit in openNewAccount of InternalAccountsController");

		return accountOpenResponse;
	}

	/**
	 * getInternalAccounts.
	 * 
	 * @param InternalAccountListRequest
	 * @return InternalAccountListResponse
	 */
	@PostMapping("/getInternalAccounts")
	public InternalAccountListResponse getInternalAccounts(
			@Valid @RequestBody InternalAccountListRequest internalAccountListRequest) {
		log.info("Enter in getInternalAccounts of InternalAccountsController");

		InternalAccountListResponse internalAccountListResponse = internalAccountsProxy
				.getInternalAccountsList(internalAccountListRequest);

		log.info("Exit in getInternalAccounts of InternalAccountsController");

		return internalAccountListResponse;

	}

}
