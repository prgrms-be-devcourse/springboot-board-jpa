package com.kdt.bulletinboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.bulletinboard.dto.PostDto;
import com.kdt.bulletinboard.dto.UserDto;
import com.kdt.bulletinboard.entity.Hobby;
import com.kdt.bulletinboard.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long savedPostId;
    private PostDto postDto;
    private UserDto userDto;

    @BeforeEach
    void saveSampleData() {
        userDto = UserDto.builder()
                .name("eonju")
                .hobby(Hobby.CYCLING)
                .build();

        postDto = PostDto.builder()
                .title("1st Post")
                .content("This is first Post")
                .userDto(userDto)
                .build();

        savedPostId = postService.save(postDto);
    }

    @Test
    void saveCallTest() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save"
                        , requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("아이디")
                                , fieldWithPath("title").type(JsonFieldType.STRING).description("제목")
                                , fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                , fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("사용자")
                                , fieldWithPath("userDto.id").type(JsonFieldType.NULL).description("사용자 아이디")
                                , fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("사용자 이름")
                                , fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("사용자 취미")
                        )
                        , responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void findOnePost() throws Exception {
        mockMvc.perform(get("/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findAllPost() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updatePost() throws Exception {
        PostDto newPostDto = PostDto.builder()
                .id(savedPostId)
                .title("updated Post")
                .content("This is updated Post")
                .userDto(userDto)
                .build();

        mockMvc.perform(put("/posts/{id}", newPostDto.getId())
                        .content(objectMapper.writeValueAsString(newPostDto))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update"
                        , requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디")
                                , fieldWithPath("title").type(JsonFieldType.STRING).description("제목")
                                , fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                , fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("사용자")
                                , fieldWithPath("userDto.id").type(JsonFieldType.NULL).description("사용자 아이디")
                                , fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("사용자 이름")
                                , fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("사용자 취미")
                        )
                        , responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }
}