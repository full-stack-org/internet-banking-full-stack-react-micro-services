package com.internet.banking.zuul.gate.way.proxy;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.internet.banking.zuul.gate.way.request.UserDetailsRequest;
import com.internet.banking.zuul.gate.way.response.UserDetailsResponse;

@FeignClient(name = "internet-banking-account-opening-service")
public interface UserDetailsProxy {

	@PostMapping("/internet/banking/password/v1/findByCustomerId")
	UserDetailsResponse getCustomeDataByCutomerId(@Valid @RequestBody UserDetailsRequest userDetailsRequest);
}
