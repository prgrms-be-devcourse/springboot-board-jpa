package com.prgrms.springbootboardjpa.user.repository;

import com.prgrms.springbootboardjpa.user.entity.Email;
import com.prgrms.springbootboardjpa.user.entity.Name;
import com.prgrms.springbootboardjpa.user.entity.Password;
import com.prgrms.springbootboardjpa.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user = new User();

    @BeforeEach
    void setUp(){
        user.setNickName("Nickname");
        user.setAge(20);
        user.setHobby("Sleep");
        user.setName(new Name("Ella","Ma"));
        user.setPassword(new Password("Password123"));
        user.setEmail(new Email("test@gmail.com"));
        user.setCreatedBy(user.getName().toString());
        user.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        userRepository.save(user);
    }

    @AfterEach
    void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        User foundUser = userRepository.findByEmail(user.getEmail());

        assertThat(foundUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void findByEmailNotExist(){
        User foundUser = userRepository.findByEmail(new Email("notExist@gmail.com"));

        assertThat(foundUser).isNull();
    }

    @Test
    void findByNickName() {
        User foundUser = userRepository.findByNickName(user.getNickName());

        assertThat(foundUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void findByNickNameNotExist(){
        User foundUser = userRepository.findByNickName("NewNickName");

        assertThat(foundUser).isNull();
    }
}