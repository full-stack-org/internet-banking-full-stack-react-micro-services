package com.internet.banking.external.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InternetBankingExternalAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetBankingExternalAccountsApplication.class, args);
	}

}
