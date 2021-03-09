package com.internet.banking.login.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.internet.banking.login.request.AuthenticationRequest;
import com.internet.banking.login.response.AuthenticationResponse;

@FeignClient(name = "internet-banking-zuul-api-gate-way")
@RibbonClient(name = "internet-banking-account-opening-service")
public interface AuthenticationProxy {

	@PostMapping("/internet-banking-account-opening-service/internet/banking/login/v1/authenticate")
	AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest);

}
