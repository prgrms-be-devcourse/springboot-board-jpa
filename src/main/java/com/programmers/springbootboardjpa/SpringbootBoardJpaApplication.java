package com.programmers.springbootboardjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootBoardJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBoardJpaApplication.class, args);
    }

}
