package com.prgrms.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class JpaBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaBoardApplication.class, args);
    }

}
