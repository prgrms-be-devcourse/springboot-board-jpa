package com.kdt.bulletinboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.bulletinboard.dto.PostDto;
import com.kdt.bulletinboard.dto.UserDto;
import com.kdt.bulletinboard.entity.Hobby;
import com.kdt.bulletinboard.service.PostService;
import com.kdt.bulletinboard.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private ObjectMapper objectMapper;

    private Long savedPostId;
    private PostDto postDto;

    @BeforeEach
    void saveSampleData() {
        UserDto userDto = UserDto.builder()
                .name("eonju")
                .hobby(Hobby.CYCLING)
                .build();
        userService.save(userDto);

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
                                fieldWithPath("id").type(JsonFieldType.NULL).description("id")
                                ,fieldWithPath("title").type(JsonFieldType.STRING).description("title")
                                ,fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                                ,fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto")
                                ,fieldWithPath("userDto.id").type(JsonFieldType.NULL).description("userDto.id")
                                ,fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name")
                                ,fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
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
}