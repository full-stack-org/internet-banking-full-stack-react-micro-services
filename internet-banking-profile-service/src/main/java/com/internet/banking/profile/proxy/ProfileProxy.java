package com.internet.banking.profile.proxy;

import javax.validation.Valid;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.internet.banking.profile.request.CustomerProfileRequest;
import com.internet.banking.profile.request.UpdateProfileRequest;
import com.internet.banking.profile.response.CustomerProfileResponse;
import com.internet.banking.profile.response.UpdateProfileResponse;

@FeignClient(name = "internet-banking-zuul-api-gate-way")
@RibbonClient(name = "internet-banking-account-opening-service")
public interface ProfileProxy {

	@PostMapping("/internet-banking-account-opening-service/internet/banking/profile/v1/updateProfile")
	UpdateProfileResponse updateProfile(@Valid @RequestBody UpdateProfileRequest updateProfileRequest);

	@PostMapping("/internet-banking-account-opening-service/internet/banking/profile/v1/getCustomerData")
	CustomerProfileResponse getCustomerProfile(@Valid @RequestBody CustomerProfileRequest customerProfileRequest);

}
