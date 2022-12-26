package com.example.springbootboard.user.repository;

import com.example.springbootboard.MySQLContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserRepositoryTest extends MySQLContainer {
    @Test
    @DisplayName("유저 생성 테스트")
    void userGenerationTest() {
        // Given

        // When

        // Then
        log.info(mySQLContainer.getDatabaseName());
    }
}
