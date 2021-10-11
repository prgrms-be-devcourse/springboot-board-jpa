package com.example.board.Service;

import com.example.board.Dto.PostDto;
import com.example.board.Dto.UserDto;
import com.example.board.Repository.PostRepository;
import com.example.board.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;


    @Test
    void insert(){

        PostDto postDto=PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();

        String result = postService.save(postDto);

        assertThat(result,is("test확인"));



    }
    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

}