package com.example.springbootboard.facade;

import com.example.springbootboard.dto.PostCreateRequest;
import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.dto.UserCreateRequest;
import com.example.springbootboard.entity.Hobby;
import com.example.springbootboard.service.PostService;
import com.example.springbootboard.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PostFacadeTest {
    @Autowired
    PostFacade postFacade;

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

//    @BeforeEach
//    void setUp(){
//
//    }
//
//    @AfterEach
//    void cleanUp(){
//
//    }

    @Test
    @DisplayName("게시물 생성 테스트")
    void postCreatTest () throws Exception {
        // Given
        userService.createUser(new UserCreateRequest("BS", 26, Hobby.MUSIC));
        PostCreateRequest request = new PostCreateRequest("test_title", "test_content", 0L);

        // When
        postFacade.createPost(request);
        PostDto postDto = postService.findPostById(0L);

        // Then
        assertThat(postDto.getTitle()).isEqualTo("test_title");
        assertThat(postDto.getContent()).isEqualTo("test_content");
    }
}
