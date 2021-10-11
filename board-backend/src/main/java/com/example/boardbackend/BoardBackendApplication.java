package com.example.boardbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardBackendApplication.class, args);
	}

}
