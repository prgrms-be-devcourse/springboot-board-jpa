package com.devcourse.springbootboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DevcousreSpringbootBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevcousreSpringbootBoardApplication.class, args);
	}

}
