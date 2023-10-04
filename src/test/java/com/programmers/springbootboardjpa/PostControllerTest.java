package com.programmers.springbootboardjpa;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.domain.user.Hobby;
import com.programmers.springbootboardjpa.dto.post.PostCreateRequest;
import com.programmers.springbootboardjpa.dto.post.PostUpdateRequest;
import com.programmers.springbootboardjpa.dto.user.UserCreateRequest;
import com.programmers.springbootboardjpa.service.PostService;
import com.programmers.springbootboardjpa.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void saveUserAndPost() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .name("changhyeon")
                .age(28)
                .hobby(Hobby.TRAVEL)
                .build();

        userService.save(userCreateRequest);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .userId(1L)
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postService.save(postCreateRequest);
    }

    @Test
    @Order(1)
    @DisplayName("게시글을 저장할 수 있다.")
    void createPost() throws Exception {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .userId(1L)
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        )
                ));
    }

    @Test
    @Order(2)
    @DisplayName("게시글을 게시글 ID로 조회할 수 있다.")
    void getPostById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findById",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("게시글 생성자"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 생성 시각"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간")
                        )
                ));
    }

    @Test
    @Order(3)
    @DisplayName("게시글을 10개씩 불러올 수 있다.")
    void getAllPost() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findAll",
                        queryParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("조회 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].createdBy").type(JsonFieldType.STRING).description("게시글 생성자"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("게시글 생성 시각"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간")
                        )
                ));

    }

    @Test
    @Order(4)
    @DisplayName("게시글의 제목과 내용를 수정할 수 있다.")
    void updatePostById() throws Exception {
        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .title("제목입니다 수정.")
                .content("내용입니다 수정.")
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-updateById",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        )
                ));
    }

    @Test
    @Order(5)
    @DisplayName("게시글을 게시글의 ID로 삭제할 수 있다.")
    void deletePostById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("post-deleteById",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        )
                ));
    }

}
