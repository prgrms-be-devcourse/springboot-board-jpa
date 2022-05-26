package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.domain.PostRepository;
import com.example.springbootboardjpa.domain.UserRepository;
import com.example.springbootboardjpa.post.dto.CreatePostRequest;
import com.example.springbootboardjpa.post.dto.PostDto;
import com.example.springbootboardjpa.post.service.PostService;
import com.example.springbootboardjpa.user.dto.CreateUserRequest;
import com.example.springbootboardjpa.user.dto.UserResponse;
import com.example.springbootboardjpa.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@DisplayName("Post API 테스트")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    UserResponse userResponse;
    PostDto postDto;

    @BeforeEach
    void setup() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .age(29)
                .name("lee")
                .hobby("thinking")
                .build();
        userResponse = userService.insert(createUserRequest);
        CreatePostRequest createPostRequest = CreatePostRequest.builder()
                .title("hello")
                .content("world")
                .userId(userResponse.getId())
                .build();
        postDto = postService.insert(createPostRequest);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물 등록 테스트")
    void POST_INSERT_TEST() throws Exception {
        CreatePostRequest createPostRequest =
                CreatePostRequest.builder()
                        .content("world")
                        .title("hello")
                        .userId(userResponse.getId())
                        .build();
        this.mockMvc.perform(put("/api/v1/posts")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.title").value(createPostRequest.getTitle()))
                .andExpect(jsonPath("$.content").value(createPostRequest.getContent()))
                .andExpect(jsonPath("$.postUserDto.id").value(createPostRequest.getUserId().toString()))
                .andDo(document("register-post",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.STRING).description("userId")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("postUserDto").type(JsonFieldType.OBJECT).description("postUserDto"),
                                fieldWithPath("postUserDto.id").type(JsonFieldType.STRING).description("postUserDto.id"),
                                fieldWithPath("postUserDto.name").type(JsonFieldType.STRING).description("postUserDto.name"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        )
                ));
    }

    @Test
    @DisplayName("전체 게시물 조회 테스트")
    void GET_POST_LIST_TEST() throws Exception {
        this.mockMvc.perform(get("/api/v1/posts")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.content[0].id").value(postDto.getId()))
                .andDo(document("getAll-post",
                        responseFields(
                                fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("content[]"),
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("content[].id"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("content[].title"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("content[].content"),
                                fieldWithPath("content[].postUserDto").type(JsonFieldType.OBJECT).description("content[].postUserDto"),
                                fieldWithPath("content[].postUserDto.id").type(JsonFieldType.STRING).description("content[].postUserDto.id"),
                                fieldWithPath("content[].postUserDto.name").type(JsonFieldType.STRING).description("content[].postUserDto.name"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("content[].createdAt"),
                                fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("content[].updatedAt"),
                                fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pageable"),
                                fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("pageable.sort"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                        )
                ));
    }

    @Test
    @DisplayName("특정 게시물 조회 테스트")
    void GET_POST_BY_ID_TEST() throws Exception {
        this.mockMvc.perform(get("/api/v1/posts/{id}", postDto.getId())
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.id").value(postDto.getId().toString()))
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()))
                .andExpect(jsonPath("$.postUserDto.id").value(postDto.getPostUserDto().getId().toString()))
                .andExpect(jsonPath("$.postUserDto.name").value(postDto.getPostUserDto().getName()))
                .andDo(document("getById-post",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("postUserDto").type(JsonFieldType.OBJECT).description("postUserDto"),
                                fieldWithPath("postUserDto.id").type(JsonFieldType.STRING).description("postUserDto.id"),
                                fieldWithPath("postUserDto.name").type(JsonFieldType.STRING).description("postUserDto.name"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        )
                ));
    }

    @Test
    @DisplayName("게시물 정보를 수정 할 수 있다.")
    void MODIFY_USER_TEST() throws Exception {
        this.mockMvc.perform(patch("/api/v1/posts")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.id").value(postDto.getId()))
                .andDo(document("modify-post",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("postUserDto").type(JsonFieldType.OBJECT).description("postUserDto"),
                                fieldWithPath("postUserDto.id").type(JsonFieldType.STRING).description("postUserDto.id"),
                                fieldWithPath("postUserDto.name").type(JsonFieldType.STRING).description("postUserDto.name"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("postUserDto").type(JsonFieldType.OBJECT).description("postUserDto"),
                                fieldWithPath("postUserDto.id").type(JsonFieldType.STRING).description("postUserDto.id"),
                                fieldWithPath("postUserDto.name").type(JsonFieldType.STRING).description("postUserDto.name"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        )
                ));
    }

    @Test
    @DisplayName("게시물을 삭제 할 수 있다.")
    void REMOVE_USER_TEST() throws Exception {
        this.mockMvc.perform(delete("/api/v1/posts/{id}", postDto.getId()))
                .andExpect(status().isNoContent())
                .andDo(document("remove-post"));
    }
}