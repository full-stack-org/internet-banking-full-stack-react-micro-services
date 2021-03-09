package com.internet.banking.forgot.password.proxy;

import javax.validation.Valid;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.internet.banking.forgot.password.request.FindByCustomerIdRequest;
import com.internet.banking.forgot.password.request.ForgotPasswordRequest;
import com.internet.banking.forgot.password.response.FindByCustomerIdResponse;
import com.internet.banking.forgot.password.response.ForgotPasswordResponse;

@FeignClient(name = "internet-banking-zuul-api-gate-way")
@RibbonClient(name = "internet-banking-account-opening-service")
public interface ForgotPasswordProxy {

	@PostMapping("/internet-banking-account-opening-service/internet/banking/password/v1/updatePassword")
	ForgotPasswordResponse updatePassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest);

	@PostMapping("/internet-banking-account-opening-service/internet/banking/password/v1/findByCustomerId")
	FindByCustomerIdResponse getCustomeDataByCutomerId(@Valid @RequestBody FindByCustomerIdRequest findByCustomerIdRequest);

}
