package com.example.board.controller;

import com.example.board.converter.PostConverter;
import com.example.board.converter.UserConverter;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.request.post.CreatePostRequest;
import com.example.board.dto.request.post.DeletePostRequest;
import com.example.board.dto.request.post.UpdatePostRequest;
import com.example.board.dto.request.user.CreateUserRequest;
import com.example.board.repository.post.PostRepository;
import com.example.board.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class PostControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    PostControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, PostRepository postRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 게시글을_생성한다() throws Exception {
        // given
        User user = generateUserByName("빙봉씨");
        CreatePostRequest requestDto = new CreatePostRequest("빙봉의 마라톤 꿀팁", "화이팅", user.getId());

        // when & then
        mockMvc.perform(post("/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andDo(document("post/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("authorId").type(JsonFieldType.NUMBER).description("게시글 작성자 ID")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("응답 데이터"),
                                fieldWithPath("datetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Test
    void 게시글을_상세_조회한다() throws Exception {
        // given
        Post post = generatePost();

        // when & then
        mockMvc.perform(get("/v1/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andDo(document("post/get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시 ID")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("datetime").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간"),
                                fieldWithPath("data.author.id").type(JsonFieldType.NUMBER).description("게시글 작성자 ID"),
                                fieldWithPath("data.author.name").type(JsonFieldType.STRING).description("게시글 작성자 이름")
                        )
                ));
    }

    @Test
    void 게시글을_전체_조회한다() throws Exception {
        // given
        generatePost();

        mockMvc.perform(get("/v1/posts")
                        .queryParams(new LinkedMultiValueMap<>() {{
                                         put("page", Collections.singletonList(null));
                                         put("size", Collections.singletonList(null));
                                         put("createdAtFrom", Collections.singletonList(null));
                                         put("createdAtTo", Collections.singletonList(null));
                                         put("sortType", Collections.singletonList(null));
                                     }}
                        ))
                .andExpect(status().isOk())
                .andDo(document("post/getAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("page").optional().description("페이지"),
                                parameterWithName("size").optional().description("가져올 로우 수"),
                                parameterWithName("createdAtFrom").optional().description("게시글 최소 생성일"),
                                parameterWithName("createdAtTo").optional().description("게시글 최대 생성일"),
                                parameterWithName("sortType").optional().description("게시글 정렬 기준 ")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("datetime").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("게시글 데이터"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간"),
                                fieldWithPath("data.content[].author.id").type(JsonFieldType.NUMBER).description("게시글 작성자 ID"),
                                fieldWithPath("data.content[].author.name").type(JsonFieldType.STRING).description("게시글 작성자 이름")
                        )
                ));
    }

    @Test
    void 게시글을_수정한다() throws Exception {
        // given
        Post post = generatePost();
        UpdatePostRequest updateRequestDto = new UpdatePostRequest("빙봉의 마라톤 꿀팁", "매일매일 뛰세요.", post.getAuthor().getId());

        // when & then
        mockMvc.perform(put("/v1/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDto)))
                .andExpect(status().isNoContent())
                .andDo(document("post/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시글 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("authorId").type(JsonFieldType.NUMBER).description("게시글 작성자 ID")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터"),
                                fieldWithPath("datetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Test
    void 게시글을_삭제한다() throws Exception {
        // given
        Post post = generatePost();
        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getAuthor().getId());
        // when & then
        mockMvc.perform(delete("/v1/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletePostRequest)))
                .andExpect(status().isNoContent())
                .andDo(document("post/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시글 ID")
                        ),
                        requestFields(
                                fieldWithPath("authorId").type(JsonFieldType.NUMBER).description("게시글 작성자 ID")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터"),
                                fieldWithPath("datetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }


    private Post generatePost() {
        User user = generateUserByName("빙봉씨");
        CreatePostRequest requestDto = new CreatePostRequest("빙봉의 마라톤 꿀팁", "러닝화를 사세요.", user.getId());
        return postRepository.save(PostConverter.toPost(requestDto, user));
    }

    private User generateUserByName(String name) {
        CreateUserRequest requestDto = new CreateUserRequest(name, "password", 20, "러닝");
        return userRepository.save(UserConverter.toUser(requestDto, passwordEncoder));
    }
}
