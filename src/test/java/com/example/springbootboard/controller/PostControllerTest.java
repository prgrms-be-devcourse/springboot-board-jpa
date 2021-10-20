package com.example.springbootboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springbootboard.converter.DtoConverter;
import com.example.springbootboard.dto.PostRequestDto;
import com.example.springbootboard.dto.UserRequestDto;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import com.example.springbootboard.service.PostService;
import com.example.springbootboard.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private DtoConverter dtoConverter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Long userId;

    private Long postId;

    @BeforeEach
    void setUp() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("testName")
                .age(26)
                .hobby("testHobby")
                .build();

        userId = userService.insert(userRequestDto);
//
//        PostRequestDto postRequestDto = PostRequestDto.builder()
//                .userId(userId)
//                .title("testTitle")
//                .content("testContent")
//                .build();
//
//        postId = postService.insert(postRequestDto);
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void insertTest() throws Exception {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .userId(userId)
                .title("testTitle")
                .content("testContent")
                .build();

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-insert",
                        requestFields(
                            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 id"),
                            fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 title"),
                            fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 content")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void getOneTest() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getOne",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 content"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void getAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("page", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getAll",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data[].postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 title"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시글 content"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void updateTest() throws Exception {
        PostRequestDto postUpdatedDto = PostRequestDto.builder()
                .userId(userId)
                .title("updatedTitle")
                .content("updatedContent")
                .build();
        mockMvc.perform(patch("/api/v1/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdatedDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 content")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 content"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/v1/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-delete",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )));

    }
}