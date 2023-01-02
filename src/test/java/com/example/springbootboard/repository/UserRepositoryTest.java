package com.example.springbootboard.repository;

import com.example.springbootboard.MySQLContainer;
import com.example.springbootboard.entity.Hobby;
import com.example.springbootboard.entity.User;
import com.example.springbootboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.example.springbootboard.entity.Hobby.getRandomHobby;
import static org.assertj.core.api.Assertions.assertThat;



// @SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest{// extends MySQLContainer {

    @Autowired
    private UserRepository repository;

    void addUserByNum(int n){
        for(int i = 0; i < n; i++){
            repository.save(User.builder().name("user"+ i ).age(i + 20).hobby(getRandomHobby()).build());
        }
    }

    @AfterEach
    void cleanUp(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("유저 생성 테스트")
    void userGenerationTest() {
        // Given
        addUserByNum(1);

        // When
        List<User> allRetrieved = repository.findAll();
        User retrieved_user = allRetrieved.get(0);

        // Then
        assertThat(allRetrieved).hasSize(1);
        assertThat(retrieved_user.getAge()).isEqualTo(20);
        assertThat(retrieved_user.getName()).isEqualTo("user0");
    }

    @Test
    @DisplayName("유저 변경 테스트")
    void  updateTest() {
        // Given

        // When

        // Then

    }
}
