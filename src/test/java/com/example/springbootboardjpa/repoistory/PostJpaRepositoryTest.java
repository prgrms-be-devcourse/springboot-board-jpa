package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.domian.Post;
import com.example.springbootboardjpa.domian.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@DataJpaTest
class PostJpaRepositoryTest {

    @Autowired
    PostJpaRepository postRepository;

    @Autowired
    UserJpaRepository userRepository;


    User user;
    Post post;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        user = new User("영지", 28);
        post = new Post("초밥 만드는 법", "좋은 사시미가 필요하다.", user);

        userRepository.save(user);
    }

    @Test
    @DisplayName("post를 id로 정상 조회한다.")
    public void findPostById(){

    }

    @Test
    @DisplayName("post를 user로 정상 조회한다")
    public void findPostByUser(){

    }

    @Test
    @DisplayName("post list를 title로 정상 조회한다.")
    public void findPostByTitle(){

    }


    @Test
    @DisplayName("post title을 정상 수정한다.")
    public void updatePostTitle(){

    }

    @Test
    @DisplayName("post content를 정상 수정한다.")
    public void updatePostContent(){

    }

    @Test
    @DisplayName("post를 정상 삭제한다.")
    public void deletePost(){

    }

}