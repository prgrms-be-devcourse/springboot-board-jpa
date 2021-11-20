package com.example.boardbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class BoardBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardBackendApplication.class, args);
	}

	// App-context 실행시 Timezone 변경
	// 참고 : https://wookcode.tistory.com/67
	@PostConstruct
	public void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}
