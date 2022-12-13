package com.ys.board.domain.post.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.board.domain.user.User;
import com.ys.board.domain.user.api.UserCreateRequest;
import com.ys.board.domain.user.repository.UserRepository;
import com.ys.board.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("Post 생성 성공 - post /api/v1/posts - Post 생성에 성공한다.")
    @Test
    void createPostSuccess() throws Exception {
        String name = "ys";
        int age = 28;

        User user = User.create(name, age, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";
        long userId = user.getId();
        PostCreateRequest postCreateRequest = new PostCreateRequest(title, content, userId);

        this.mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequest)))
            .andExpect(status().isCreated())
            .andDo(document("posts-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성 유저 Id")
                ),
                responseFields(
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 Id")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 생성 실패 - post /api/v1/posts - 유저가 없으므로 Post 생성에 실패한다.")
    @Test
    void createPostFailNotFoundUser() throws Exception {

        String title = "title";
        String content = "content";
        long userId = 0L;
        PostCreateRequest postCreateRequest = new PostCreateRequest(title, content, userId);

        this.mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequest)))
            .andExpect(status().isNotFound())
            .andDo(document("posts-create-user-notfound",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성 유저 Id")
                ),
                responseFields(
                    fieldWithPath("timeStamp").description("서버 응답 시간"),
                    fieldWithPath("message").description("예외 메시지"),
                    fieldWithPath("requestUrl").description("요청한 url")
                )
            ))
            .andDo(print());
    }
}