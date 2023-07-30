package com.board.server.domain.post.controller;

import com.board.server.domain.post.dto.request.CreatePostRequest;
import com.board.server.domain.post.dto.request.UpdatePostRequest;
import com.board.server.domain.post.entity.Post;
import com.board.server.domain.post.entity.PostRepository;
import com.board.server.domain.post.service.PostService;
import com.board.server.domain.user.entity.User;
import com.board.server.domain.user.entity.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
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
    ObjectMapper objectMapper;

    User user1;
    Post post1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .name("밍키")
                .age(19).build();
        user1.setHobby("공놀이");

        userRepository.save(user1);

        CreatePostRequest createPostRequest = new CreatePostRequest("제목1", "내용1");
        Long postId = postService.createPost(createPostRequest, user1.getUserId()).postId();
        post1 = postRepository.findById(postId).get();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void saveTest() throws Exception {
        CreatePostRequest createPostRequest = new CreatePostRequest("제목test", "내용test");

        mockMvc.perform(post("/post")
                        .header("userId", user1.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @Test
    void getAllPostTest() throws Exception {
        mockMvc.perform(get("/post")
                        .param("page", String.valueOf(1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all-post",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.postResponses").type(JsonFieldType.ARRAY).description("게시글 리스트"),
                                fieldWithPath("data.postResponses[].postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.postResponses[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.postResponses[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.postResponses[].createdBy").type(JsonFieldType.STRING).description("createdBy"),
                                fieldWithPath("data.postResponses[].createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("data.pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보 조회"),
                                fieldWithPath("data.pageInfo.totalPageSize").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("data.pageInfo.currentPageIndex").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("data.pageInfo.isEnd").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부")
                        )
                ));
    }

    @Test
    void getOnePostTest() throws Exception {
        mockMvc.perform(get("/post/{postId}", post1.getPostId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-one-post",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @Test
    void updatePostTest() throws Exception {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest("바뀐 제목", null);

        mockMvc.perform(patch("/post/{postId}", post1.getPostId())
                        .header("userId", post1.getUser().getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-post",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

}