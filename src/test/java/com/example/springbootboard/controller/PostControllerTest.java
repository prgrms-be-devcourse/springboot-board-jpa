package com.example.springbootboard.controller;

import com.example.springbootboard.dto.PostCreateRequest;
import com.example.springbootboard.facade.PostFacade;
import com.example.springbootboard.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PostService postService;
    @Autowired
    PostFacade postFacade;
    @Autowired
    ObjectMapper objectMapper;

//    @BeforeEach
//    void setUp(){
//        PostCreateRequest postCreateRequest = new PostCreateRequest("test_title_0", "test_content_0", 0L);
//    }

    @Test
    @DisplayName("게시물 생성 테스트")
    void postSaveTest () throws Exception {
        PostCreateRequest postCreateRequest = new PostCreateRequest("test_title_0", "test_content_0", 0L);
        postFacade.createPost(postCreateRequest);

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

    }
}
