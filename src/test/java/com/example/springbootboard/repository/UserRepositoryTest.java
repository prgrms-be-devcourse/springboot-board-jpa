package com.example.springbootboard.repository;

import com.example.springbootboard.entity.Post;
import com.example.springbootboard.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    Long userId;

    @BeforeEach
    void save() {
        User user = User.builder()
                .name("testName")
                .age(21)
                .hobby("testHobby")
                .build();

        user.addPost(Post.builder()
                .title("testTitle")
                .content("testContent")
                .build());

        User userEntity = userRepository.save(user);

        assertTrue(Objects.nonNull(userEntity.getId()));
        userId = userEntity.getId();
    }

    @Test
    void findOne() {
        // given

        // when
        Optional<User> maybeUser = userRepository.findById(userId);
        // then
        assertTrue(maybeUser.isPresent());
    }

    @Test
    void findAll() {
        // given

        // when
        List<User> all = userRepository.findAll();
        // then
        assertNotEquals(all.size(), 0);
    }
}