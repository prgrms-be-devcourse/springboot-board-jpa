package com.kdt.postproject.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.postproject.domain.post.PostRepository;
import com.kdt.postproject.post.dto.PostDto;
import com.kdt.postproject.post.dto.UserDto;
import com.kdt.postproject.post.service.PostService;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    Long id = 1L;

    @BeforeEach
    void setUp() {
        //Given
        PostDto postDto = PostDto.builder()
                .title("test")
                .content("testing....")
                .userDto(
                        UserDto.builder()
                                .name("bnminji")
                                .age(26)
                                .hobby("running")
                                .build()
                )
                .build();

//        PostDto postDto1 = PostDto.builder()
//                .title("test+")
//                .content("testing+....")
//                .userDto(
//                        UserDto.builder()
//                                .name("bnminji")
//                                .age(26)
//                                .hobby("running")
//                                .build()
//                )
//                .build();
        //When
        Long id = postService.save(postDto);
//        Long id1 = postService.save(postDto1);

        //Then
        assertThat(id).isEqualTo(1L);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void saveTest() throws Exception{
        // Given
        PostDto postDto = PostDto.builder()
                .title("test")
                .content("testing....")
                .userDto(
                        UserDto.builder()
                                .name("bnminji")
                                .age(26)
                                .hobby("running")
                                .build()
                )
                .build();
        //When //Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())//200
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("데이터.title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("데이터.content"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("데이터.userDto.name"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("데이터.userDto.age"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("데이터.userDto.hobby"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void updateTest() throws Exception{
        // Given
        PostDto updateDto = PostDto.builder()
                .id(1L)
                .title("update")
                .content("updating....")
                .userDto(
                        UserDto.builder()
                                .name("bnminji")
                                .age(26)
                                .hobby("running")
                                .build()
                )
                .build();
        //When //Then
        mockMvc.perform(post("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())//200
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
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

    @Test
    void getOneTest() throws Exception {
        mockMvc.perform(get("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andDo(document("post-getone",
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

    @Test
    void getAllTest() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getall"));
    }
}