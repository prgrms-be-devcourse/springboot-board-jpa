package com.kdt.springbootboard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kdt.springbootboard.common.RestDocsConfiguration;
import com.kdt.springbootboard.dto.post.PostCreateRequest;
import com.kdt.springbootboard.dto.post.PostResponse;
import com.kdt.springbootboard.dto.post.PostUpdateRequest;
import com.kdt.springbootboard.dto.user.UserCreateRequest;
import com.kdt.springbootboard.dto.user.UserResponse;
import com.kdt.springbootboard.repository.PostRepository;
import com.kdt.springbootboard.repository.UserRepository;
import com.kdt.springbootboard.service.PostService;
import com.kdt.springbootboard.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@SpringBootTest
class PostControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PostService postService;
    @Autowired private UserService userService;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;

    private Long userId;
    private Long postId;

    @BeforeEach
    void setUp() throws Exception {
        // given // when
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
            .name("전찬의")
            .email("jcu011@naver.com")
            .age("29")
            .hobby("watching Netflix")
            .build();
        userId = userService.insert(userCreateRequest);
        UserResponse userResponse = userService.findById(userId);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .userId(userId)
            .title("TDD is dead")
            .content("They check functionality, but checking alone does not add up to testing: testing = checking + exploring")
            .build();
        postId = postService.insert(postCreateRequest);
        PostResponse postResponse = postService.findById(postId);

        // then
        assertThat(userResponse.getName()).isEqualTo(userCreateRequest.getName());
        assertThat(userResponse.getEmail()).isEqualTo(userCreateRequest.getEmail());
        assertThat(userResponse.getAge()).isEqualTo(userCreateRequest.getAge());
        assertThat(userResponse.getHobby()).isEqualTo(userCreateRequest.getHobby());
        assertThat(postResponse.getTitle()).isEqualTo(postCreateRequest.getTitle());
        assertThat(postResponse.getContent()).isEqualTo(postCreateRequest.getContent());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글등록")
    public void insertPostTest() throws Exception {
        // given
        PostCreateRequest request = PostCreateRequest.builder()
            .userId(userId)
            .title("Agile 4 values")
            .content("1. Individuals and interactions over processes and tools\n"
                    +"2. Working software over comprehensive documentation\n"
                    +"3. Customer collaboration over contract negotiation\n"
                    +"4. Responding to change over following a plan\n")
            .build();

        // when // then
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("PostController/insertPost",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("name"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("email"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("age")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data").description(JsonFieldType.NUMBER).description("data")
                )
            ));
    }

    @Test
    @DisplayName("게시글조회")
    public void getPostTest() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("PostController/getPost",
                pathParameters(
                    parameterWithName("id").description(JsonFieldType.NUMBER).description("userId")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("data.id"),
                    fieldWithPath("data.title").description(JsonFieldType.STRING).description("data.title"),
                    fieldWithPath("data.content").description(JsonFieldType.STRING).description("data.content")
                )
            ));
    }

    @Test
    @DisplayName("모든_게시글_페이징_조회")
    public void getAllPostTest() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                .param("page", String.valueOf(0)) // 페이지
                .param("size", String.valueOf(10)) // 사이즈
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
        // Todo : 페이징 조회 문서화
    }

    @Test
    @DisplayName("게시글_회원으로_페이징_조회")
    public void getAllPostByUserIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/posts/user/{id}", userId)
                .param("page", String.valueOf(0)) // 페이지
                .param("size", String.valueOf(10)) // 사이즈
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
        // Todo : 페이징 조회 문서화
    }

    @Test
    @DisplayName("게시글수정")
    public void updatePostTest() throws Exception {
        // given
        PostUpdateRequest request = PostUpdateRequest.builder()
            .id(postId)
            .title("Agile 4 values")
            .content("1. Individuals and interactions over processes and tools\n"
                +"2. Working software over comprehensive documentation\n"
                +"3. Customer collaboration over contract negotiation\n"
                +"4. Responding to change over following a plan\n")
            .build();

        // when // then
        mockMvc.perform(put("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("PostController/updatePost",
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data.id").description(JsonFieldType.NUMBER).description("data.id"),
                    fieldWithPath("data.title").description(JsonFieldType.STRING).description("data.title"),
                    fieldWithPath("data.content").description(JsonFieldType.STRING).description("data.content")
                )
            ));
    }

    @Test
    @DisplayName("게시글삭제")
    public void deletePostTest() throws Exception {
        mockMvc.perform(delete("/api/v1/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("PostController/deletePost",
                pathParameters(
                    parameterWithName("id").description(JsonFieldType.NUMBER).description("id")
                ),
                responseFields(
                    fieldWithPath("statusCode").description(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("serverDatetime").description(JsonFieldType.STRING).description("serverDatetime"),
                    fieldWithPath("data").description(JsonFieldType.NUMBER).description("data")
                )
            ));
    }

}