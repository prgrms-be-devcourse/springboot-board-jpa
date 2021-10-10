package com.example.board.domain;

import com.example.board.Repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
class PostUserMapping {
    @Autowired
    PostRepository postRepository;
    @BeforeEach
    public void delete(){
        postRepository.deleteAll();
    }


    @Test
    public void mappingTest(){

        User user=new User();
        user.setId(3L);
        user.setAge(24);
        user.setName("박연수");
        user.setHobby("놀기");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("박연수");

        Post post=new Post();
        post.setPostId(1L);
        post.setContent("게시판 과제를 잘하려면 어떻게 해야하나요 알려주세용");
        post.setTitle("미션을 잘하고 싶어요");
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        postRepository.save(post);
//        assertThat(post,samePropertyValuesAs(user.getPost().get(0)));


    }


}