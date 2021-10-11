package com.assignment.bulletinboard;

import com.assignment.bulletinboard.post.PostRepository;
import com.assignment.bulletinboard.user.User;
import com.assignment.bulletinboard.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class UserTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = User.builder()
                .age(20)
                .name("홍길동")
                .hobby("축구")
                .build();
        user1.setCreatedAt(LocalDateTime.now());
        user1.setCreatedBy("admin");

        userRepository.save(user1);

        User user2 = User.builder()
                .age(23)
                .name("김둘리")
                .hobby("농구")
                .build();

        user2.setCreatedAt(LocalDateTime.now());
        user2.setCreatedBy("admin");

        userRepository.save(user2);
    }

    @Test
    @DisplayName("User를 조회할 수 있다.")
    void find_user() {
        List<User> findAllUser = userRepository.findAll();
        findAllUser.stream().forEach(user -> log.info(user.getName()));
    }

    @Test
    @DisplayName("User를 수정할 수 있다.")
    void update_user() {
        Optional<User> beforeUpdate = userRepository.findById(1L);

        beforeUpdate.ifPresent(selectUser ->{
            selectUser.changeUserName("왕서방");
            selectUser.changeUserHobby("서예");
            userRepository.save(selectUser);
                });

        Optional<User> afterUpdate = userRepository.findById(1L);

        log.info("{}의 취미는 {}!", afterUpdate.get().getName(), afterUpdate.get().getHobby());
    }

    @Test
    @DisplayName("User를 삭제할 수 있다.")
    void delete_user() {
        log.info("기존의 userRepository "+String.valueOf(userRepository.findAll()));

        userRepository.deleteAll();

        log.info("삭제 후 userRepository "+String.valueOf(userRepository.findAll()));
    }
}
