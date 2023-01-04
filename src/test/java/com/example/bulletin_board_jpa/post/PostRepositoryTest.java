package com.example.bulletin_board_jpa.post;

import com.example.bulletin_board_jpa.user.User;
import com.example.bulletin_board_jpa.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    private User user;
    @BeforeEach
    void setup() {
        // user 저장
        user = new User();
        user.setName("이동준");
        user.setAge(27);
        user.setHobby("기타 치기");

        userRepository.save(user);
    }



    @Test
    void insert() {



        // post 저장
        Post post = new Post();
        post.setTitle("오늘의 일기");
        post.setContent("사람들과 함께 즐거운 식사~");
        post.setUser(user);

        postRepository.save(post);


    }
}