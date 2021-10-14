package com.programmers.springbootboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboard.dto.PostRequestDto;
import com.programmers.springbootboard.entity.Post;
import com.programmers.springbootboard.entity.User;
import com.programmers.springbootboard.repository.PostRepository;
import com.programmers.springbootboard.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).name("username").age(10).hobby("coding").build();
        userRepository.save(user);
    }

    @AfterEach
    void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("포스트 조회 MVC 테스트")
    void testGetPostMVC() throws Exception {
        // Given
        Post post = Post.builder().title("title").content("content").user(user).build();
        postRepository.save(post);

        // When & Then
        mockMvc.perform(get("/api/v1/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-post",
                        pathParameters(
                                parameterWithName("id").description("post id")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("Is successful response"),
                                fieldWithPath("status_code").type(JsonFieldType.NUMBER).description("status code of response"),
                                fieldWithPath("http_method").type(JsonFieldType.STRING).description("http method of request"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("post info"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title of post"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content of post"),
                                fieldWithPath("data.post_id").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("data.created_by").type(JsonFieldType.STRING).description("post created by"),
                                fieldWithPath("data.created_at").type(JsonFieldType.STRING).description("post created at")
                        )));
    }

    @Test
    @DisplayName("포스트 페이지 조회 MVC 테스트")
    void testGetPostsMVC() throws Exception {
        // Given
        for (int i = 0; i < 5; i++) {
            postRepository.save(Post.builder().title("title" + i).content("content" + i).user(user).build());
        }

        // When & Then
        mockMvc.perform(get("/api/v1/posts?page=1&size=2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-posts",
                        requestParameters(
                                parameterWithName("page").description("페이지 넘버"),
                                parameterWithName("size").description("페이지 당 포스트 수")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("Is successful response"),
                                fieldWithPath("status_code").type(JsonFieldType.NUMBER).description("status code of response"),
                                fieldWithPath("http_method").type(JsonFieldType.STRING).description("http method of request"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data retrieved"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("posts"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title of post"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content of post"),
                                fieldWithPath("data.content[].post_id").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("data.content[].created_by").type(JsonFieldType.STRING).description("post created by"),
                                fieldWithPath("data.content[].created_at").type(JsonFieldType.STRING).description("post created at"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("pagable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
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
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty")
                        )));
    }

    @Test
    @DisplayName("포스트 생성 MVC 테스트")
    void testCreatePostMVC() throws Exception {
        // Given
        PostRequestDto dto = PostRequestDto.builder().title("title").content("content").username("username").build();

        // When & Then
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("create-post",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("id (IGNORED)"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("username").type(JsonFieldType.STRING).description("username")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("Is successful response"),
                                fieldWithPath("status_code").type(JsonFieldType.NUMBER).description("status code of response"),
                                fieldWithPath("http_method").type(JsonFieldType.STRING).description("http method of request"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("created post info"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title of post"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content of post"),
                                fieldWithPath("data.post_id").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("data.created_by").type(JsonFieldType.STRING).description("post created by"),
                                fieldWithPath("data.created_at").type(JsonFieldType.STRING).description("post created at")
                        )));
    }

    @Test
    @DisplayName("포스트 수정 MVC 테스트")
    void testUpdatePostMVC() throws Exception {
        // Given
        Post post = Post.builder().title("title").content("content").user(user).build();
        postRepository.save(post);
        PostRequestDto dto = PostRequestDto.builder().id(post.getId()).title("title2").content("content2").username("username").build();

        // When & Then
        mockMvc.perform(put("/api/v1/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-post",
                        pathParameters(
                                parameterWithName("id").description("post id")
                        ),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("username").type(JsonFieldType.STRING).description("username (IGNORED)")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("Is successful response"),
                                fieldWithPath("status_code").type(JsonFieldType.NUMBER).description("status code of response"),
                                fieldWithPath("http_method").type(JsonFieldType.STRING).description("http method of request"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("updated post info"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title of post"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content of post"),
                                fieldWithPath("data.post_id").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("data.created_by").type(JsonFieldType.STRING).description("post created by"),
                                fieldWithPath("data.created_at").type(JsonFieldType.STRING).description("post created at")
                        )));
    }

    @Test
    @DisplayName("에러 응답 테스트")
    void testErrorResponse() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{id}", 999))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("error-response",
                        pathParameters(
                                parameterWithName("id").description("post id")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("Is successful response"),
                                fieldWithPath("status_code").type(JsonFieldType.NUMBER).description("status code of response"),
                                fieldWithPath("http_method").type(JsonFieldType.STRING).description("http method of request"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("error message"),
                                fieldWithPath("data.error_message").type(JsonFieldType.STRING).description("error details")
                        )));
    }

}
