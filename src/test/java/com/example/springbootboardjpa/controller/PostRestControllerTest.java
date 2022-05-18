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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void createTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("Hello")
                .content("nice to meet you")
                .customer(
                        CustomerDto.builder()
                                .name("santa")
                                .age(20)
                                .hobby("writing")
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

    @Test
    void getTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("Hello")
                .content("nice to meet you")
                .customer(
                        CustomerDto.builder()
                                .name("santa")
                                .age(20)
                                .hobby("writing")
                                .build()
                )
                .build();

        mockMvc.perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postDto))
                );

        mockMvc.perform(
                        get("/api/v1/posts/{id}",1)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-read",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.customer").type(JsonFieldType.OBJECT).description("customer"),
                                fieldWithPath("data.customer.id").type(JsonFieldType.NUMBER).description("customer id"),
                                fieldWithPath("data.customer.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.customer.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.customer.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.customer.created_by").type(JsonFieldType.STRING).description("customer created_by"),
                                fieldWithPath("data.customer.created_at").type(JsonFieldType.STRING).description("customer created_at"),
                                fieldWithPath("data.created_by").type(JsonFieldType.STRING).description("created_by"),
                                fieldWithPath("data.created_at").type(JsonFieldType.STRING).description("created_at"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")

                        )
                ));
    }

    @Test
    void updateTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("Hello")
                .content("nice to meet you")
                .customer(
                        CustomerDto.builder()
                                .name("santa")
                                .age(20)
                                .hobby("writing")
                                .build()
                )
                .build();

        mockMvc.perform(
                post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
        );

        PostDto postDto1 = PostDto.builder()
                .id(1L)
                .title("bye")
                .content("see you again")
                .customer(
                        CustomerDto.builder()
                                .name("santa")
                                .age(20)
                                .hobby("writing")
                                .build()
                )
                .build();

        mockMvc.perform(
                        post("/api/v1/posts/{id}",1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postDto1))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("customer").type(JsonFieldType.OBJECT).description("customer"),
                                fieldWithPath("customer.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("customer.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("customer.hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )
                ));
    }

    @Test
    void deleteTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("Hello")
                .content("nice to meet you")
                .customer(
                        CustomerDto.builder()
                                .name("santa")
                                .age(20)
                                .hobby("writing")
                                .build()
                )
                .build();

        mockMvc.perform(
                post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
        );

        mockMvc.perform(
                        delete("/api/v1/posts/{id}",1)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-delete",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )
                ));
    }
}