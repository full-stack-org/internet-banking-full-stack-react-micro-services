package com.internet.banking.forgot.password.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.forgot.password.proxy.ForgotPasswordProxy;
import com.internet.banking.forgot.password.request.FindByCustomerIdRequest;
import com.internet.banking.forgot.password.request.ForgotPasswordRequest;
import com.internet.banking.forgot.password.response.FindByCustomerIdResponse;
import com.internet.banking.forgot.password.response.ForgotPasswordResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ineternet/password/v1")
@Slf4j
public class ForgotPasswordController {

	@Autowired
	private ForgotPasswordProxy forgotPasswordProxy;

	/**
	 * findByCustomerId.
	 * 
	 * @param findByCustomerIdRequest
	 * @return FindByCustomerIdResponse
	 */
	@PostMapping("/findByCustomerId")
	public FindByCustomerIdResponse findByCustomerId(
			@Valid @RequestBody FindByCustomerIdRequest findByCustomerIdRequest) {
		log.info("Enter in findByCustomerId of ForgotPasswordController");

		FindByCustomerIdResponse findByCustomerIdResponse = forgotPasswordProxy
				.getCustomeDataByCutomerId(findByCustomerIdRequest);

		log.info("Exit in findByCustomerId of ForgotPasswordController");

		return findByCustomerIdResponse;

	}

	/**
	 * 
	 * @param forgotPasswordRequest
	 * @return
	 */
	@PostMapping("/updatePassword")
	public ForgotPasswordResponse updatePassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
		log.info("Enter in updatePassword of ForgotPasswordController");

		ForgotPasswordResponse forgotPasswordResponse = forgotPasswordProxy.updatePassword(forgotPasswordRequest);

		log.info("Exit in updatePassword of ForgotPasswordController");

		return forgotPasswordResponse;
	}
}
