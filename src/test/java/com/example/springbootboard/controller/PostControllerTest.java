package com.example.springbootboard.controller;

import com.example.springbootboard.converter.PostConverter;
import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.domain.vo.Name;
import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.dto.UserDto;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import com.example.springbootboard.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostConverter postConverter;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void set_up() {
        User user = new User(new Name("허승연"), 26, "운동");
        userRepository.save(user);
    }

    @BeforeEach
    void save_test() {
        // Given
        PostDto postDto = new PostDto("제목", "내용", 1L);

        // When
        Long id = postService.save(postDto);

        Post findPost = postRepository.findById(id).get();
        // Then
        assertThat(findPost, samePropertyValuesAs(postConverter.convertPost(postDto), "createdAt", "updatedAt", "createdBy", "id"));
    }

    @Test
    void saveCallTest() throws Exception {
        // Given
        PostDto postDto = new PostDto("제목", "내용", 1L);

        // When // Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk()) // 200
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("createdBy").type(JsonFieldType.NUMBER).description("createdBy")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getOne",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }


    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getAll",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                subsectionWithPath("data").description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

}