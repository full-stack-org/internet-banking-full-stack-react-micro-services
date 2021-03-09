package com.internet.banking.account.open.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.account.open.request.CustomerProfileRequest;
import com.internet.banking.account.open.request.UpdateProfileRequest;
import com.internet.banking.account.open.response.CustomerProfileResponse;
import com.internet.banking.account.open.response.UpdateProfileResponse;
import com.internet.banking.account.open.service.AccountOpenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/banking/profile/v1")
@Slf4j
public class ProfileController {

	@Autowired
	private AccountOpenService accountOpenService;

	/**
	 * getProfileData.
	 * 
	 * @param customerProfileRequest
	 * @return CustomerProfileResponse
	 */
	@PostMapping("/getCustomerData")
	public CustomerProfileResponse getProfileData(@Valid @RequestBody CustomerProfileRequest customerProfileRequest) {
		log.info("Enter in getProfileData of UpdateProfileController");

		CustomerProfileResponse customerProfileResponse = accountOpenService
				.getCustomerProfileData(customerProfileRequest);

		log.info("Exit in getProfileData of UpdateProfileController");

		return customerProfileResponse;
	}

	/**
	 * updateProfile.
	 * 
	 * @param updateProfileRequest
	 * @return UpdateProfileResponse
	 */
	@PostMapping("/updateProfile")
	public UpdateProfileResponse updateProfile(@Valid @RequestBody UpdateProfileRequest updateProfileRequest) {
		log.info("Enter in updateProfile of UpdateProfileController");

		UpdateProfileResponse updateProfileResponse = accountOpenService.updateProfile(updateProfileRequest);

		log.info("Exit in updateProfile of UpdateProfileController");

		return updateProfileResponse;
	}

}
