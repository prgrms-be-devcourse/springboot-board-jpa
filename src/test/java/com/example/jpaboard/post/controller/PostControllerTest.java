package com.example.jpaboard.post.controller;

import com.example.jpaboard.post.controller.mapper.PostApiMapper;
import com.example.jpaboard.post.service.PostService;
import com.example.jpaboard.post.service.dto.FindAllRequest;
import com.example.jpaboard.post.service.dto.PostResponse;
import com.example.jpaboard.post.service.dto.PostResponses;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @MockBean
    private PostController postController;

    @MockBean
    private PostApiMapper postApiMapper;

//    @Test
//    void findAllBy() {
//        //given
//        String title = "엄청 맛있는 맛집을 발견했어요";
//        String content = "프로그래머스 주변 어쩌구저쩌구 타코집이 맛있습니다.";
//        Pageable pageable = PageRequest.of(0, 10);
//
//        FindAllRequest findAllRequest = new FindAllRequest(title,content);
//        Slice<PostResponse> results =
//
//
//        given(postService.findPostAllByFilter(findAllRequest,pageable)).willReturn();

        //
//
//    }

    @Test
    void updatePost() {
    }

    @Test
    void findById() {
    }

    @Test
    void savePost() {
    }
}