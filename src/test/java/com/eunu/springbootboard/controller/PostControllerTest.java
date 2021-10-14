package com.eunu.springbootboard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eunu.springbootboard.domain.post.PostDto;
import com.eunu.springbootboard.domain.post.PostService;
import com.eunu.springbootboard.domain.user.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    String uuid = UUID.randomUUID().toString();
    Long postId = 1L;

    private PostDto postDto = PostDto.builder()
        .id(postId)
        .title("title1")
        .content("content1")
        .build();

    private UserDto userDto = UserDto.builder()
        .id(uuid)
        .name("eunu")
        .age(20)
        .hobby("")
        .postDtos(List.of(
            postDto
        ))
        .build();

    //@Test
    @BeforeEach
    void setUp() {
        //Given

        //When
        Long savedPostId = postService.save(postDto);
        //Then
        assertThat(postId).isEqualTo(savedPostId);
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    //Doc
    @Test
    void saveCallTest() throws Exception {
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto))
            )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document
                (
                    "post-save",
                    requestFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                    ),
                    responseFields(
                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                    )
                )
            );
    }
}