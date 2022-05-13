package com.programmers.epicblues.jpa_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpaPracticeBoardApplication {

  public static void main(String[] args) {
    SpringApplication.run(JpaPracticeBoardApplication.class, args);
  }

}
