package com.kdt.devcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringJpaBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringJpaBoardApplication.class, args);
    }
}
