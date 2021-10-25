package com.kdt.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.domain.Post;
import com.kdt.board.dto.post.PostRequest;
import com.kdt.board.dto.user.UserRequest;
import com.kdt.board.service.PostService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    ObjectMapper objectMapper;

    private static UserRequest userRequest;
    private static PostRequest postRequest;
    private static Long setPostId;
    private static Post post;

    @BeforeEach
    void setUp() {

        userRequest = new UserRequest("set user");

        postRequest = new PostRequest("set title", "set content", userRequest.getName());

        setPostId = postService.createPost(postRequest);

    }

    @Test
    void createPost() throws Exception {
        // TODO: 2021-10-26 new PostRequest() 말고 post.toPostRequest()로 했을 때 안 됐는데 다시 확인
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PostRequest("new title", "new content", "new user"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("create-post",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("TITLE"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("CONTENT"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("USER NAME")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                        ));
    }

    // question : comments가 빈 리스트이면 comments의 값들은 불러올 수 없는건가?
    @Test
    public void getOnePost() throws Exception{
        mockMvc.perform(get("/posts/{postId}", setPostId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-one-post",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시글 Id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.comments[]").type(JsonFieldType.ARRAY).description("댓글 리스트"),
//                                fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
//                                fieldWithPath("data.comments[].userName").type(JsonFieldType.STRING).description("댓글 작성자"),
//                                fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
//                                fieldWithPath("data.comments[].createdAt").type(JsonFieldType.NULL).description("댓글 생성날짜"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("게시글 작성자"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.NULL).description("게시글 생성날짜"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    public void getAllPosts() throws Exception {
        mockMvc.perform(get("/posts/{postId}", setPostId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updatePost() throws Exception {
        mockMvc.perform(put("/posts/{postId}", setPostId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PostRequest("updated title", "updated content", "updated user"))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deletePost() throws Exception{
        mockMvc.perform(delete("/posts/{postId}", setPostId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}