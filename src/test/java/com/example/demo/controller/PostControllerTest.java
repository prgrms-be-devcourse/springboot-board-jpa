package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
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

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Long savedPostId;

    @BeforeEach
    void setUpSave(RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

        UserDto userDto = UserDto.builder()
                .name("jamie")
                .age(3)
                .hobby("dance")
                .build();
        PostDto postDto = PostDto.builder()
                .title("test_title")
                .content("내용입니다. contents")
                .userDto(userDto)
                .build();

        savedPostId = postService.save(postDto);

        assertThat(savedPostId).isNotZero();
    }


    @Test
    void save_test() throws Exception {
        UserDto userDto = UserDto.builder()
                .name("jamie")
                .age(3)
                .hobby("dance")
                .build();
        PostDto postDto = PostDto.builder()
                .title("test_title")
                .content("내용입니다. contents")
                .userDto(userDto)
                .build();

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NULL).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                        )
                ));
    }

    @Test
    void getOne_test() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-one",
                        pathParameters(parameterWithName("id").description("id")),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        )
                ));
    }

    @Test
    @DisplayName("없는 postId를 가져오려고하면 핸들러가 동작한다")
    void getUnknownOne_test() throws Exception {
        mockMvc.perform(get("/posts/{id}", new Random().nextLong())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andDo(print());
    }

    @Test
    void getAllByPageTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all",
                        requestParameters(
                                parameterWithName("page").description("page"),
                                parameterWithName("size").description("size")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime"),

                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("data.content[]"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("data.content[].id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("data.content[].title"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("data.content[].content"),
                                fieldWithPath("data.content[].userDto.id").type(JsonFieldType.NUMBER).description("data.content[].userDto.id"),
                                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("data.content[].userDto.name"),
                                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("data.content[].userDto.age"),
                                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("data.content[].userDto.hobby"),

                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),

                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),

                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),

                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty")
                        )
                ));
    }

    @Test
    void updateTitleAndContent() throws Exception {
        UserDto userDto = UserDto.builder()
                .name("jamie")
                .age(3)
                .hobby("dance")
                .build();
        PostDto postDto = PostDto.builder()
                .title("change_title")
                .content("변경된 내용입니다. change_contents")
                .userDto(userDto)
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-one",
                        pathParameters(
                                parameterWithName("id").description("id")
                        ),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NULL).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        )
                ));
    }
}