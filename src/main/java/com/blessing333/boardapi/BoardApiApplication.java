package com.blessing333.boardapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApiApplication.class, args);
    }

}
