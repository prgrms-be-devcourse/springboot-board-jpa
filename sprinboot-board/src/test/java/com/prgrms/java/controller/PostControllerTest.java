package com.prgrms.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.domain.Post;
import com.prgrms.java.domain.User;
import com.prgrms.java.dto.post.CreatePostRequest;
import com.prgrms.java.dto.post.ModifyPostRequest;
import com.prgrms.java.repository.PostRepository;
import com.prgrms.java.repository.UserRepository;
import com.prgrms.java.service.PostService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("게시글을 페이징 조회할 수 있다.")
    @Test
    void getPosts() throws Exception {
        User user = userRepository.save(createUser());
        Post post1 = createPost(user);
        Post post2 = new Post("데브코스 짱짱2", "데브코스 짱짱2입니다.", user);
        postRepository.saveAll(List.of(post1, post2));

        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(getCookie()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-getAll",
                                responseFields(
                                        fieldWithPath("postDetails[]").type(JsonFieldType.ARRAY).description("페이지의 게시판들"),
                                        fieldWithPath("postDetails[].id").type(JsonFieldType.NUMBER).description("게시판 아이디"),
                                        fieldWithPath("postDetails[].title").type(JsonFieldType.STRING).description("게시판 제목"),
                                        fieldWithPath("postDetails[].content").type(JsonFieldType.STRING).description("게시판 내용")
                                )
                        )
                );
    }

    @DisplayName("게시글을 상세 조회할 수 있다.")
    @Test
    void getPostDetails() throws Exception {
        User user = userRepository.save(createUser());
        Post post = postRepository.save(new Post("데브코스 짱짱2", "데브코스 짱짱2입니다.", user));

        mockMvc.perform(get("/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(getCookie()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-getDetails",
                                pathParameters(
                                        parameterWithName("id").description("게시판 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시판 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")
                                )
                        )
                );

    }

    @DisplayName("게시글을 등록할 수 있다.")
    @Test
    void createPost() throws Exception {
        User user = userRepository.save(createUser());

        CreatePostRequest createPostRequest = new CreatePostRequest(
                "데브코스 짱짱",
                "데브코스 짱짱입니다."
        );

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest))
                        .cookie(getCookie()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-create",
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")
                                ),
                                responseFields(
                                        fieldWithPath("createdId").type(JsonFieldType.NUMBER).description("생성된 게시판 아이디")
                                )
                        )
                );
    }

    @DisplayName("제목이 없는 게시글은 등록할 수 없다.")
    @Test
    void createPostEmptyTitle() throws Exception {
        User user = userRepository.save(createUser());

        CreatePostRequest createPostRequest = new CreatePostRequest(
                "",
                "데브코스 짱짱입니다."
        );

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest))
                        .cookie(getCookie()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("post-create-title-fail",
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지")
                                )
                        )
                );
    }

    @DisplayName("내용이 없는 게시글은 등록할 수 없다.")
    @Test
    void createPostEmptyContent() throws Exception {
        User user = userRepository.save(createUser());

        CreatePostRequest createPostRequest = new CreatePostRequest(
                "데브코스 짱짱",
                ""
        );

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest))
                        .cookie(getCookie()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("post-create-content-fail",
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지")
                                )
                        )
                );
    }

    @DisplayName("게시글을 수정할 수 있다.")
    @Test
    void modifyPost() throws Exception {
        User user = userRepository.save(createUser());
        Post post = postRepository.save(createPost(user));

        ModifyPostRequest request = new ModifyPostRequest("데브코스 좋아", "데브코스 좋아용!");

        mockMvc.perform(put("/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .cookie(getCookie()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-modify",
                                pathParameters(
                                        parameterWithName("id").description("작성자 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
                                ),
                                responseFields(
                                        fieldWithPath("modifiedId").type(JsonFieldType.NUMBER).description("수정된 게시판 아이디")
                                )
                        )
                );
    }

    @DisplayName("빈 문자열로 게시글 제목을 수정할 수 없다.")
    @Test
    void modifyPostEmptyTitle() throws Exception {
        User user = userRepository.save(createUser());
        Post post = postRepository.save(createPost(user));

        ModifyPostRequest request = new ModifyPostRequest("", "데브코스 좋아용!");

        mockMvc.perform(put("/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .cookie(getCookie()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("post-modify-title-fail",
                                pathParameters(
                                        parameterWithName("id").description("작성자 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지")
                                )
                        )
                );
    }

    @DisplayName("빈 문자열로 게시글 내용을 수정할 수 없다.")
    @Test
    void modifyPostEmptyContent() throws Exception {
        User user = userRepository.save(createUser());
        Post post = postRepository.save(createPost(user));

        ModifyPostRequest request = new ModifyPostRequest("데브코스 좋아", "");

        mockMvc.perform(put("/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .cookie(getCookie()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("post-modify-content-fail",
                                pathParameters(
                                        parameterWithName("id").description("작성자 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지")
                                )
                        )
                );
    }

    private static Post createPost(User user) {
        return new Post("데브코스 짱짱", "데브코스 짱짱입니다.", user);
    }

    private User createUser() {
        return new User("이택승", "test@gmail.com", "test", 25, HobbyType.MOVIE);
    }

    private Cookie getCookie() {
        return new Cookie("login-token", "test@gmail.com");
    }
}