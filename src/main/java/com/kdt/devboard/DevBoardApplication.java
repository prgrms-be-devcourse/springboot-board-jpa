package com.kdt.devboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DevBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevBoardApplication.class, args);
    }

}
