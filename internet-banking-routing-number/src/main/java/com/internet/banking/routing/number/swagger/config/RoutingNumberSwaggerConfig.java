package com.internet.banking.routing.number.swagger.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class RoutingNumberSwaggerConfig {
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("internet-banking-routing-number").apiInfo(apiInfo())
				.select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex("/internet/routing/v1/.*"), regex("/internet/routing/v1/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("internet-banking-routing-number")
				.description("internet-banking-routing-number API reference for developers").version("1.0").build();
	}
}
