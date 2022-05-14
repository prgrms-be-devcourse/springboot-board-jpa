package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.CustomerDto;
import com.example.springbootboardjpa.dto.PostDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("인사")
                .content("안녕하세요")
                .customer(
                        CustomerDto.builder()
                                .name("김산타")
                                .age(20)
                                .hobby("책읽기")
                                .build()
                )
                .build();

        mockMvc.perform(
                post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                           fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                           fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                           fieldWithPath("customer").type(JsonFieldType.OBJECT).description("customer"),
                           fieldWithPath("customer.name").type(JsonFieldType.STRING).description("name"),
                           fieldWithPath("customer.age").type(JsonFieldType.NUMBER).description("age"),
                           fieldWithPath("customer.hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                           fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                           fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                           fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )
                ));
    }
}