package com.example.board.Repository;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    LocalDateTime now=LocalDateTime.now();
    @BeforeEach
    void insert(){
        postRepository.deleteAll();
        Post post=new Post();
        post.setPostId(1L);
        post.setContent("게시판 과제를 잘하려면 어떻게 해야하나요 알려주세용");
        post.setTitle("미션을 잘하고 싶어요");
        post.setCreatedAt(now);
        post.setCreatedBy("박연수");

        postRepository.save(post);
        log.info("{}",postRepository.findAll());

    }
    @Test
    void read(){
        Post post1 = postRepository.findById(1L).get();
        Post newpost=new Post();
        newpost.setPostId(1L);
        newpost.setContent("게시판 과제를 잘하려면 어떻게 해야하나요 알려주세용");
        newpost.setTitle("미션을 잘하고 싶어요");
        newpost.setCreatedAt(now);
        newpost.setCreatedBy("박연수");
        assertThat(post1,samePropertyValuesAs(newpost));
    }
    //update 메소드 정의해서 사용하기로 해요~
    @Test
    @Transactional
    void update(){
        Post post2 = postRepository.findById(1L).get();
        post2.setTitle("게시판 과제 어려워요");
        log.info("{}",postRepository.findAll());

    }
    @Test
    void validation(){
        User user=new User();

        assertThrows(IllegalArgumentException.class,()->user.setAge(-1));
        assertThrows(IllegalArgumentException.class,()->user.setAge(201));

    }


}