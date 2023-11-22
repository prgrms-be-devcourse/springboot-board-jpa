package com.programmers.springboard.dto;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@TestConfiguration
public class ValidatorConfig {

	@Bean
	public ValidatorFactory validatorFactory(){
		return Validation.buildDefaultValidatorFactory();
	}

	@Bean
	public Validator validator(ValidatorFactory factory){
		return factory.getValidator();
	}
}
