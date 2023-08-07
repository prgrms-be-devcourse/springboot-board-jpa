package com.prgrms.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(
        auditorAwareRef = "customAuditAware",
        dateTimeProviderRef = "customAuditAware"
)
public class SpringbootBoardJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBoardJpaApplication.class, args);
    }

}
