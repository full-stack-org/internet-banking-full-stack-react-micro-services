package com.internet.banking.zuul.gate.way.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
	private int customerId;
	private StatusResponse statusResponse;
}
