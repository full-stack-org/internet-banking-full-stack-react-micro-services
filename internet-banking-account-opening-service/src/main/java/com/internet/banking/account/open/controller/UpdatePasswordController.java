package com.internet.banking.account.open.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.account.open.request.FindByCustomerIdRequest;
import com.internet.banking.account.open.request.UpdatePasswordRequest;
import com.internet.banking.account.open.response.FindByCustomerIdResponse;
import com.internet.banking.account.open.response.UpdatePasswordResponse;
import com.internet.banking.account.open.service.AccountOpenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/banking/password/v1")
@Slf4j
public class UpdatePasswordController {

	@Autowired
	private AccountOpenService accountOpenService;

	/**
	 * findByCustomerId.
	 * 
	 * @param findByCustomerIdRequest
	 * @return FindByCustomerIdResponse
	 */
	@PostMapping("/findByCustomerId")
	public FindByCustomerIdResponse findByCustomerId(
			@Valid @RequestBody FindByCustomerIdRequest findByCustomerIdRequest) {
		log.info("Enter in findByCustomerId of UpdatePasswordController");

		FindByCustomerIdResponse findByCustomerIdResponse = accountOpenService
				.getCustomeDataByCutomerId(findByCustomerIdRequest);

		log.info("Exit in findByCustomerId of UpdatePasswordController");

		return findByCustomerIdResponse;

	}

	/**
	 * updatePassword.
	 * 
	 * @param updatePasswordRequest
	 * @return UpdatePasswordResponse
	 */
	@PostMapping("/updatePassword")
	public UpdatePasswordResponse updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
		log.info("Enter in updatePassword of UpdatePasswordController");

		UpdatePasswordResponse updatePasswordResponse = accountOpenService.updatePassword(updatePasswordRequest);

		log.info("Exit in updatePassword of UpdatePasswordController");

		return updatePasswordResponse;

	}
}
