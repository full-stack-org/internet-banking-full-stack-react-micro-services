package com.internet.banking.profile.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet.banking.profile.proxy.ProfileProxy;
import com.internet.banking.profile.request.CustomerProfileRequest;
import com.internet.banking.profile.request.UpdateProfileRequest;
import com.internet.banking.profile.response.CustomerProfileResponse;
import com.internet.banking.profile.response.UpdateProfileResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/internet/profile/v1")
@Slf4j
public class ProfileController {
	
	@Autowired
	private ProfileProxy profileProxy;

	
	/**
	 * updateProfile.
	 * 
	 * @param customerProfileRequest
	 * @return UpdateProfileResponse
	 */
	@PostMapping("/getCustomerProfile")
	public CustomerProfileResponse getCustomerProfile(@Valid @RequestBody CustomerProfileRequest customerProfileRequest) {
		log.info("Enter in updateProfile of ProfileController");

		CustomerProfileResponse customerProfileResponse = profileProxy.getCustomerProfile(customerProfileRequest);

		log.info("Exit in updateProfile of ProfileController");

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
		log.info("Enter in updateProfile of ProfileController");

		UpdateProfileResponse updateProfileResponse = profileProxy.updateProfile(updateProfileRequest);

		log.info("Exit in updateProfile of ProfileController");

		return updateProfileResponse;
	}
}
