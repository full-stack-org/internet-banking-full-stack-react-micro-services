package com.internet.banking.account.open.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.account.open.request.InternalAccountListRequest;
import com.internet.banking.account.open.response.InternalAccountListResponse;
import com.internet.banking.account.open.service.AccountOpenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/banking/accounts/v1")
@Slf4j
public class GetInternalAccountsController {

	@Autowired
	private AccountOpenService accountOpenService;

	/**
	 * findByCustomerId.
	 * 
	 * @param findByCustomerIdRequest
	 * @return FindByCustomerIdResponse
	 */
	@PostMapping("/getInternalAccounts")
	public InternalAccountListResponse getInternalAccounts(
			@Valid @RequestBody InternalAccountListRequest internalAccountListRequest) {
		log.info("Enter in getInternalAccounts of GetInternalAccountsController");

		InternalAccountListResponse internalAccountListResponse = accountOpenService
				.getInternalAccountsList(internalAccountListRequest);

		log.info("Exit in getInternalAccounts of GetInternalAccountsController");

		return internalAccountListResponse;

	}
}
