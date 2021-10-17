package org.programmers.project_board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.project_board.dto.PostDto;
import org.programmers.project_board.dto.UserDto;
import org.programmers.project_board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("모든 게시글을 불러올 수 있다")
    void getAllPosts() throws Exception {
        // given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("yekyeong")
                .age(23)
                .hobby("watching netflix")
                .build();

        PostDto postDto = PostDto.builder()
                .title("title1")
                .content("content1")
                .userDto(userDto)
                .build();
        PostDto postDto2 = PostDto.builder()
                .title("title2")
                .content("content2")
                .userDto(userDto)
                .build();

        postService.savePost(postDto);
        postService.savePost(postDto2);

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-all"
                        , responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("posts"),
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("post.id"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("post.title"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("post.content"),
                                fieldWithPath("[].userDto").type(JsonFieldType.OBJECT).description("user"),
                                fieldWithPath("[].userDto.id").type(JsonFieldType.NUMBER).description("user.id"),
                                fieldWithPath("[].userDto.name").type(JsonFieldType.STRING).description("user.name"),
                                fieldWithPath("[].userDto.age").type(JsonFieldType.NUMBER).description("user.age"),
                                fieldWithPath("[].userDto.hobby").type(JsonFieldType.STRING).description("user.hobby")
                        )
                ));
    }

    @Test
    @DisplayName("원하는 id값의 게시글을 불러올 수 있다")
    void getPost() throws Exception {
        // given
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .userDto(UserDto.builder()
                        .id(1L)
                        .name("yekyeong")
                        .age(23)
                        .hobby("watching netflix")
                        .build()
                )
                .build();

        Long id = postService.savePost(postDto);

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-one"
                        , pathParameters(parameterWithName("id").description("id"))
                        , responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("user"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("user.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("user.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("user.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("user.hobby")
                        )
                ));
    }

    @Test
    @DisplayName("새 게시글을 저장할 수 있다")
    void createPost() throws Exception {
        // given
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("title")
                .content("content")
                .userDto(UserDto.builder()
                        .id(1L)
                        .name("yekyeong")
                        .age(23)
                        .hobby("watching netflix")
                        .build()
                )
                .build();

        // when // then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-create"
                        , requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("user"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("user.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("user.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("user.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("user.hobby")
                        )
                ));
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다")
    void updatePost() throws Exception {
        // given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("yekyeong")
                .age(23)
                .hobby("watching netflix")
                .build();

        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .userDto(userDto)
                .build();

        Long id = postService.savePost(postDto);

        PostDto updatedPostDto = PostDto.builder()
                .title("updatedTitle")
                .content("updateContent")
                .userDto(userDto)
                .build();

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPostDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update"
                        , pathParameters(parameterWithName("id").description("id"))
                        , requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("user"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("user.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("user.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("user.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("user.hobby")
                        )
                ));
    }
}