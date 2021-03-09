package com.internet.banking.routing.number;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InternetBankingRoutingNumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetBankingRoutingNumberApplication.class, args);
	}

}
