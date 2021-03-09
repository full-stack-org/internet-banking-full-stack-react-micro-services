package com.internet.banking.zuul.gate.way.user;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.internet.banking.zuul.gate.way.proxy.UserDetailsProxy;
import com.internet.banking.zuul.gate.way.request.UserDetailsRequest;
import com.internet.banking.zuul.gate.way.response.UserDetailsResponse;

@Service
public class CustomerDataService implements UserDetailsService {

	@Autowired
	private UserDetailsProxy userDetailsProxy;

	@Override
	public UserDetails loadUserByUsername(String userName) {

		UserDetailsRequest userDetailsRequest = new UserDetailsRequest();
		userDetailsRequest.setCustomerId(Integer.parseInt(userName));

		UserDetailsResponse userDetailsResponse = userDetailsProxy.getCustomeDataByCutomerId(userDetailsRequest);

		String customerId = String.valueOf(userDetailsResponse.getCustomerId());

		return new org.springframework.security.core.userdetails.User(customerId, customerId, new ArrayList<>());
	}

}
