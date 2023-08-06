package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.RestDocs.AbstractRestDocesTest;
import com.example.springbootboardjpa.dto.post.request.PostCreateRequest;
import com.example.springbootboardjpa.dto.post.request.PostUpdateRequest;
import com.example.springbootboardjpa.dto.user.request.UserCreateRequest;
import com.example.springbootboardjpa.service.PostService;
import com.example.springbootboardjpa.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.springbootboardjpa.enums.Hobby.GAME;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class PostControllerTest extends AbstractRestDocesTest {

    private final ObjectMapper objectMapper;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostControllerTest(RestDocumentationResultHandler resultHandler, MockMvc mockMvc, ObjectMapper objectMapper,
                              UserService userService, PostService postService) {
        super(resultHandler, mockMvc);
        this.objectMapper = objectMapper;
        this.postService = postService;
        this.userService = userService;
    }

    @BeforeAll()
    void savePostandUser() {
        UserCreateRequest userCreateRequest = new UserCreateRequest("Kim Jae won", 28, GAME);
        userService.createUser(userCreateRequest);

        PostCreateRequest postCreateRequest1 = new PostCreateRequest("kim jae won", "타일러 팀원의 멘토님은 kim jae won입니다!!", 1L);
        PostCreateRequest postCreateRequest2 = new PostCreateRequest("so jae hoon", "타일러 팀원은 최고입니다!!!", 1L);
        postService.createPost(postCreateRequest1);
        postService.createPost(postCreateRequest2);
    }

    @Test
    @DisplayName("[REST DOCS] CREATE Post")
    @Transactional
    void createPostTest() throws Exception {
        PostCreateRequest request = new PostCreateRequest("황창현", "황창현은 바부입니다!", 1L);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(resultHandler.document(
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시물 작성 일자"),
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("게시물 작성자")
                        )
                ));

    }

    @Test
    @DisplayName("[REST DOCS] GET All posts")
    void findByUserAllTest() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(resultHandler.document(
                        responseFields(
                                fieldWithPath("[]postId").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("[]title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("[]content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("[]userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("[]createdAt").type(JsonFieldType.STRING).description("게시물 작성일시"),
                                fieldWithPath("[]createdBy").type(JsonFieldType.STRING).description("게시물 작성자")
                        )
                ));
    }

    @Test
    @DisplayName("[REST DOCS] GET post by ID")
    void findByUserByIdTest() throws Exception {
        long postId = 1;

        mockMvc.perform(get("/api/posts/{id}", postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(resultHandler.document(
                        pathParameters(
                                parameterWithName("id").description("조회할 게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시물 작성일시"),
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("게시물 작성자")
                        )
                ));
    }

    @Test
    @DisplayName("[REST DOCS] UPDATE user")
    @Transactional
    void updatePostTest() throws Exception {
        long postId = 1L;
        PostUpdateRequest request = new PostUpdateRequest("황창현 바부", "황창현은 엄청난 바부입니다.");

        mockMvc.perform(patch("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(resultHandler.document(
                        pathParameters(
                                parameterWithName("id").description("수정할 게시물 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 게시물 내용")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시물 작성 일자"),
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("게시물 작성자")
                        )
                ));
    }

    @Test
    @DisplayName("[REST DOCS] DELETE post by ID")
    @Transactional
    void deleteByPostByIdTest() throws Exception {
        long postId = 1;

        mockMvc.perform(delete("/api/posts/{id}", postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(resultHandler.document(
                        pathParameters(
                                parameterWithName("id").description("삭제할 게시물 ID")
                        )
                ));
    }
}
