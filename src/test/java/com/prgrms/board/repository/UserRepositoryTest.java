package com.prgrms.board.repository;

import com.prgrms.board.domain.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }
    
    @Test
    @DisplayName("유저 생성")
    public void create() {
        //== given ==//
        Users user = new Users("lee@test.com", "lee", 10);
        //== when ==//
        Users savedUser = userRepository.save(user);
        //== then ==//
        assertThat(savedUser).isNotNull();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    @DisplayName("유저 정보 수정")
    public void update() {

        //== given ==//
        Users user = new Users("lee@test.com", "lee", 10);
        Users savedUser = userRepository.save(user);
        //== when ==//
        String changeName = "kim";
        savedUser.updateUser(changeName, null);
        //== then ==//
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    @DisplayName("유저 삭제")
    public void delete() {

        //== given ==//
        Users user = new Users("lee@test.com", "lee", 10);
        Users savedUser = userRepository.save(user);
        //== when ==//
        userRepository.delete(savedUser);
        Users foundUser = userRepository.findById(savedUser.getUserId()).orElse(null);
        //== then ==//
        assertThat(foundUser).isNull();
    }
}