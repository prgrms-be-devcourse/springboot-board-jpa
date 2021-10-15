package com.programmers.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.board.dto.PostDto;
import com.programmers.board.dto.UserDto;
import com.programmers.board.repository.PostRepository;
import com.programmers.board.service.PostService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private final Long id = 1L;

    @BeforeEach
    void save() {
        // Given
        PostDto postDto = PostDto.builder()
                //.id(id)
                .title("여기에는 게시물 제목")
                .content("여기에는 게시물 내용")
                .userDto(
                        UserDto.builder()
                                .name("오재욱")
                                .age(28)
                                .hobby("영화 감상")
                                .build()
                ).build();

        // When
        Long savedId = postService.save(postDto);

        // Then
        assertThat(savedId).isEqualTo(id);
    }

    @AfterEach
    void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 작업을 테스트합니다")
    void saveTest() throws Exception {
        // Given
        PostDto postDto = PostDto.builder()
                .id(id)
                .title("여기에는 게시물 제목")
                .content("여기에는 게시물 내용")
                .userDto(
                        UserDto.builder()
                                .id(id)
                                .name("오재욱")
                                .age(28)
                                .hobby("영화 감상")
                                .build()
                ).build();

        // When, Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk()) // 200
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("단일 조회를 테스트합니다")
    void getOneTest() throws Exception {
        mockMvc.perform(get("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getOne",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("전체 조회를 테스트합니다")
    void getAllTest() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getAll"));
    }

    @Test
    @DisplayName("수정 작업을 테스트합니다")
    void updateTest() throws Exception{
        // Given
        PostDto updatedPostDto = PostDto.builder()
                .id(id)
                .title("여기에는 게시물 제목")
                .content("여기에는 게시물 내용")
                .userDto(
                        UserDto.builder()
                                .id(id)
                                .name("오재욱")
                                .age(28)
                                .hobby("영화 감상")
                                .build()
                ).build();
        //When //Then
        mockMvc.perform(post("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPostDto)))
                .andExpect(status().isOk())//200
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("데이터.id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("데이터.title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("데이터.content"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("데이터.userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("데이터.userDto.name"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("데이터.userDto.age"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("데이터.userDto.hobby"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

}