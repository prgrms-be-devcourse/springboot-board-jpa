package com.kdt.board.repository;

import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CommentRepositoryTest {
    Long id = 1L;

    @Autowired
    CommentRepository repository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("이하정")
                .build();

        Post post = Post.builder()
                .title("title은 제목")
                .content("content는 내용")
                .user(user)
                .build();

    }
}