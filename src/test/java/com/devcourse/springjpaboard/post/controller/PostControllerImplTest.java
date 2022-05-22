package com.devcourse.springjpaboard.post.controller;

import com.devcourse.springjpaboard.post.service.PostService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostControllerImplTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    void writePostTest() {

    }
}