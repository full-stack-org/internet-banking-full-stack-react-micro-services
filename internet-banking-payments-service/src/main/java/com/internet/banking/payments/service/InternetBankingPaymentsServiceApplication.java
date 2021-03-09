package com.internet.banking.payments.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InternetBankingPaymentsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetBankingPaymentsServiceApplication.class, args);
	}

}
