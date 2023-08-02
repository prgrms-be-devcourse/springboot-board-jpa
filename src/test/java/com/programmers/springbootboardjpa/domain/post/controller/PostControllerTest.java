package com.programmers.springbootboardjpa.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.domain.post.dto.PostCreateRequestDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostResponseDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostUpdateRequestDto;
import com.programmers.springbootboardjpa.domain.post.service.PostService;
import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserResponseDto;
import com.programmers.springbootboardjpa.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    private PostCreateRequestDto postCreateRequestDto;

    private Long userId;

    @BeforeEach
    void setUp() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("김이름")
                .age(27)
                .hobby("산책")
                .build();

        UserResponseDto userResponseDto = userService.create(userRequestDto);
        userId = userResponseDto.id();

        postCreateRequestDto = PostCreateRequestDto.builder()
                .title("등록용 제목")
                .content("등록용 내용")
                .userId(userId)
                .build();
    }

    @DisplayName("게시글 등록 성공")
    @Test
    void create() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(postCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ), responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("게시글 등록 실패")
    @Test
    void createFail() throws Exception {
        //given
        postCreateRequestDto = PostCreateRequestDto.builder()
                .title(" ")
                .content("등록용 내용")
                .userId(userId)
                .build();

        //when
        //then
        mockMvc.perform(post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(postCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-create-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ), responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data")
                        )
                ));
    }

    @DisplayName("게시글 페이징 조회 성공")
    @Test
    void findAll() throws Exception {
        //given
        postService.create(postCreateRequestDto);

        //when
        //then
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-get-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty").ignored(),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted").ignored(),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted").ignored(),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset").ignored(),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged").ignored(),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged").ignored(),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last").ignored(),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size").ignored(),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number").ignored(),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty").ignored(),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted").ignored(),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted").ignored(),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first").ignored(),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements").ignored(),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty").ignored()
                        )
                ));
    }

    @DisplayName("게시글 단건 조회 성공")
    @Test
    void findById() throws Exception {
        //given
        PostResponseDto postResponseDto = postService.create(postCreateRequestDto);
        Long savedPostId = postResponseDto.id();

        //when
        //then
        mockMvc.perform(get("/api/v1/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-get-one",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("게시글 단건 조회 실패")
    @Test
    void findByIdFail() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/posts/{id}", 11111L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-get-one-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data")
                        )
                ));
    }

    @DisplayName("게시글 수정 성공")
    @Test
    void update() throws Exception {
        //given
        PostResponseDto postResponseDto = postService.create(postCreateRequestDto);
        Long savedPostId = postResponseDto.id();

        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("수정용 제목")
                .content("수정용 내용")
                .build();

        //when
        //then
        mockMvc.perform(put("/api/v1/posts/{id}", savedPostId)
                        .content(objectMapper.writeValueAsString(postUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("게시글 수정 실패")
    @Test
    void updateFail() throws Exception {
        //given
        PostResponseDto postResponseDto = postService.create(postCreateRequestDto);
        Long savedPostId = postResponseDto.id();

        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("수정용 제목")
                .content(" ")
                .build();

        //when
        //then
        mockMvc.perform(put("/api/v1/posts/{id}", savedPostId)
                        .content(objectMapper.writeValueAsString(postUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-update-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data")
                        )
                ));
    }
}