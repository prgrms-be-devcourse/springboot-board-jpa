package com.example.spring_jpa_post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringJpaPostApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringJpaPostApplication.class, args);
    }
}
