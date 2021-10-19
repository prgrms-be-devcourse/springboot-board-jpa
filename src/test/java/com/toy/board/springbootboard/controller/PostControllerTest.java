package com.toy.board.springbootboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.board.springbootboard.model.converter.PostConverter;
import com.toy.board.springbootboard.model.dto.PostDto;
import com.toy.board.springbootboard.model.dto.UserDto;
import com.toy.board.springbootboard.model.repository.PostRepository;
import com.toy.board.springbootboard.model.repository.UserRepository;
import com.toy.board.springbootboard.model.service.PostService;
import javassist.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostConverter postConverter;

    private Long userId;

    @BeforeEach
    void setUp() throws NotFoundException {
        userId = userRepository.save(postConverter.convertUser(UserDto.builder()
                        .id(1L)
                        .name("minkyu")
                        .age(26)
                        .hobby("programming")
                        .build()))
                .getId();
    }

    @Test
    @Order(1)
    @DisplayName("Post 작성 요청 테스트")
    void savePostTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .userId(userId)
                .id(1L)
                .title("testTitle2")
                .content("testContent2")
                .build();

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    @Order(2)
    @DisplayName("Posts 조회 요청 테스트")
    void getPostsTest() throws Exception {
        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("postList"));
    }

    @Test
    @Order(3)
    @DisplayName("Id로 Post 조회 요청 테스트")
    void IdgetPostTest() throws Exception {
        mockMvc.perform(get("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("postById",
                        responseFields(
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }

    @Test
    @Order(4)
    @DisplayName("post 수정 요청 테스트")
    void UpdatePostTest() throws Exception {
        PostDto UpdatePostDto = PostDto.builder()
                .userId(userId)
                .id(1L)
                .title("updateTitle2")
                .content("updateContent2")
                .build();
        mockMvc.perform(post("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UpdatePostDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }
}