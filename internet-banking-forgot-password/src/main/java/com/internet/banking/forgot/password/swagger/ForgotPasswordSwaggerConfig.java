package com.internet.banking.forgot.password.swagger;

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
public class ForgotPasswordSwaggerConfig {
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("internet-banking-forgot-password")
				.apiInfo(apiInfo()).select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex("/internet/password/.*"), regex("/internet/password/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("internet-banking-forgot-password")
				.description("internet-banking-forgot-password API reference for developers").version("1.0")
				.build();
	}
}
