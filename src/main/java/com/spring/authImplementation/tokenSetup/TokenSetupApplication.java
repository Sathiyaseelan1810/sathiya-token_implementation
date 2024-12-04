package com.spring.authImplementation.tokenSetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TokenSetupApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(TokenSetupApplication.class, args);
		Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(
				System.out::println);

	}



}
