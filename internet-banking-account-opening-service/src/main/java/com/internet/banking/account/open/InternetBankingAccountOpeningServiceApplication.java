package com.internet.banking.account.open;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InternetBankingAccountOpeningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetBankingAccountOpeningServiceApplication.class, args);
	}

}
