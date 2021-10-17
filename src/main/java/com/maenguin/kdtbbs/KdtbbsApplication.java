package com.maenguin.kdtbbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KdtbbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KdtbbsApplication.class, args);
	}

}
