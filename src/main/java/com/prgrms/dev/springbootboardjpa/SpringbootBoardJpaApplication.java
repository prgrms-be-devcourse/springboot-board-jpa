package com.prgrms.dev.springbootboardjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringbootBoardJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBoardJpaApplication.class, args);
    }

}
