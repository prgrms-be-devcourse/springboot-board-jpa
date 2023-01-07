package com.example.bulletin_board_jpa.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void insert() {
        User user1 = new User(); // 준양속상태
        user1.setName("이동준");
        user1.setAge(28);
        user1.setHobby("기타 치기");

        userRepository.save(user1); // 준영속 상태의 객체를 넘기면서

        User user2 = new User();
        user2.setName("이유진");
        user2.setAge(25);
        user2.setHobby("놀기");

        userRepository.save(user2);
        Optional<User> userX = userRepository.findByAgeGreaterThan(26);
        System.out.println(userX.get().getName());

        List<User> list = userRepository.findAll();
        for (User user : list) {
            System.out.println(user.getId());
        }
    }
}