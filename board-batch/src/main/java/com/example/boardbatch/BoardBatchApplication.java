package com.example.boardbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class BoardBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardBatchApplication.class, args);
    }

}
