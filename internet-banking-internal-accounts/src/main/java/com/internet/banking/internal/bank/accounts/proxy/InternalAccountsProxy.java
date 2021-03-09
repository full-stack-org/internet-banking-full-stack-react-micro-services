package com.internet.banking.internal.bank.accounts.proxy;

import javax.validation.Valid;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.internet.banking.internal.bank.accounts.request.AccountOpenRequest;
import com.internet.banking.internal.bank.accounts.request.InternalAccountListRequest;
import com.internet.banking.internal.bank.accounts.response.AccountOpenResponse;
import com.internet.banking.internal.bank.accounts.response.InternalAccountListResponse;

@FeignClient(name = "internet-banking-zuul-api-gate-way")
@RibbonClient(name = "internet-banking-account-opening-service")
public interface InternalAccountsProxy {

	@PostMapping("/internet-banking-account-opening-service/internet/banking/v1/openNewAccount")
	AccountOpenResponse openNewAccount(@Valid @RequestBody AccountOpenRequest accountOpenRequest);

	@PostMapping("/internet-banking-account-opening-service/internet/banking/accounts/v1/getInternalAccounts")
	InternalAccountListResponse getInternalAccountsList(
			@Valid @RequestBody InternalAccountListRequest internalAccountListRequest);

}
