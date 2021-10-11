package com.prgrms.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.domain.post.PostRepository;
import com.prgrms.board.domain.user.UserRepository;
import com.prgrms.board.post.dto.PostDto;
import com.prgrms.board.post.dto.UserDto;
import com.prgrms.board.post.service.PostService;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    private final Long id = 1L;

    private UserDto userDto;

    private PostDto postDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(id)
                .name("이범진")
                .age(33)
                .hobby("독서")
                .build();

        postDto = PostDto.builder()
                .id(id)
                .title("게시글 제목")
                .content("게시글 내용")
                .userDto(userDto)
                .build();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @DisplayName("게시물 저장 테스트")
    @Test
    void savePost() throws Exception {
        //when //then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터")
                        )
                ));
    }

    @DisplayName("게시물 단건 조회 테스트")
    @Test
    void getOnePost() throws Exception {
        //when //then
        mockMvc.perform(post("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("게시물 페이지 조회 테스트")
    @Test
    void getAllPosts() throws Exception {
        //when //then
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0)) //0페이지
                        .param("size", String.valueOf(10)) //10사이즈 조회
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}