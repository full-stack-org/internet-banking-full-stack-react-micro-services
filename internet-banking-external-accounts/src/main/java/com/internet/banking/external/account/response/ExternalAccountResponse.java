package com.internet.banking.external.account.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ExternalAccountResponse {
	private String accountNumber;
	private String accountType;
	private String accountNickName;
	private StatusResponse statusResponse;
}
