package com.example.springbootboard.repository;

import com.example.springbootboard.MySQLContainer;
import com.example.springbootboard.entity.Hobby;
import com.example.springbootboard.entity.User;
import com.example.springbootboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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

import static org.assertj.core.api.Assertions.assertThat;



// @SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest{// extends MySQLContainer {

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("유저 생성 테스트")
    void userGenerationTest() {
        // Given
        String tmpName = "tmp_name";
        int tmpAge = 25;
        Hobby tmpHobby = Hobby.MUSIC;
        User user = User.builder().name(tmpName).age(tmpAge).hobby(tmpHobby).build();
        repository.save(user);

        // When
        List<User> allRetrieved = repository.findAll();
        User retrieved_user = allRetrieved.get(0);

        // Then
        assertThat(allRetrieved).hasSize(1);
        assertThat(retrieved_user.getAge()).isEqualTo(tmpAge);
        assertThat(retrieved_user.getHobby()).isEqualTo(tmpHobby);
        assertThat(retrieved_user.getName()).isEqualTo(tmpName);
    }
}
