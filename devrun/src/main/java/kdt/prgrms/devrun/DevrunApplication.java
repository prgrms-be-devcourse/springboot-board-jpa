package kdt.prgrms.devrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DevrunApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevrunApplication.class, args);
    }

}
