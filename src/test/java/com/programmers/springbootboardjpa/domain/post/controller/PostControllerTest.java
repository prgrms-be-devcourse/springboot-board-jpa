package com.programmers.springbootboardjpa.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.domain.post.dto.PostCreateRequestDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostResponseDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostUpdateRequestDto;
import com.programmers.springbootboardjpa.domain.post.service.PostService;
import com.programmers.springbootboardjpa.global.error.ErrorCode;
import com.programmers.springbootboardjpa.global.error.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @DisplayName("게시글 등록 성공")
    @Test
    void create() throws Exception {
        //given
        PostCreateRequestDto postCreateRequestDto = PostCreateRequestDto.builder()
                .title("등록용 제목")
                .content("등록용 내용")
                .userId(1L)
                .build();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(1L)
                .title("등록용 제목")
                .content("등록용 내용")
                .userId(1L)
                .createdBy(null)
                .createdAt(LocalDateTime.now())
                .build();

        when(postService.create(any(PostCreateRequestDto.class))).thenReturn(postResponseDto);

        //when
        //then
        mockMvc.perform(post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(postCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postResponseDto.id()))
                .andExpect(jsonPath("$.title").value("등록용 제목"))
                .andExpect(jsonPath("$.content").value("등록용 내용"))
                .andExpect(jsonPath("$.userId").value(postResponseDto.userId()))
                .andDo(document("post-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ), responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("게시글 등록 실패")
    @Test
    void createFail() throws Exception {
        //given
        PostCreateRequestDto postCreateRequestDto = PostCreateRequestDto.builder()
                .title(" ")
                .content("등록용 내용")
                .userId(1L)
                .build();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(1L)
                .title(" ")
                .content("등록용 내용")
                .userId(1L)
                .createdBy(null)
                .createdAt(LocalDateTime.now())
                .build();

        when(postService.create(any(PostCreateRequestDto.class))).thenReturn(postResponseDto);

        //when
        //then
        mockMvc.perform(post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(postCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value("INVALID_ENTITY_VALUE"))
                .andExpect(jsonPath("$.message").value("제목을 입력해주세요."))
                .andDo(document("post-create-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ), responseFields(
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("timestamp"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("error").type(JsonFieldType.STRING).description("error"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message")
                        )
                ));
    }

    @DisplayName("게시글 페이징 조회 성공")
    @Test
    void findAll() throws Exception {
        //given
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(1L)
                .title("등록용 제목")
                .content("등록용 내용")
                .userId(1L)
                .createdBy(null)
                .createdAt(LocalDateTime.now())
                .build();

        List<PostResponseDto> postResponseDtos = List.of(postResponseDto);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<PostResponseDto> postResponseDtoPage = new PageImpl<>(postResponseDtos, pageRequest, postResponseDtos.size());
        when(postService.findAll(any(Pageable.class))).thenReturn(postResponseDtoPage);

        //when
        //then
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(postResponseDto.id()))
                .andExpect(jsonPath("$.content[0].title").value("등록용 제목"))
                .andExpect(jsonPath("$.content[0].content").value("등록용 내용"))
                .andExpect(jsonPath("$.content[0].userId").value(postResponseDto.userId()))
                .andDo(document("post-get-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("content[].userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("content[].createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty").ignored(),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted").ignored(),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted").ignored(),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset").ignored(),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged").ignored(),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged").ignored(),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("data.last").ignored(),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size").ignored(),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number").ignored(),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty").ignored(),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted").ignored(),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted").ignored(),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first").ignored(),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements").ignored(),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty").ignored()
                        )
                ));
    }

    @DisplayName("게시글 단건 조회 성공")
    @Test
    void findById() throws Exception {
        //given
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(1L)
                .title("등록용 제목")
                .content("등록용 내용")
                .userId(1L)
                .createdBy(null)
                .createdAt(LocalDateTime.now())
                .build();

        when(postService.findById(any(Long.class))).thenReturn(postResponseDto);

        //when
        //then
        mockMvc.perform(get("/api/v1/posts/{id}", postResponseDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postResponseDto.id()))
                .andExpect(jsonPath("$.title").value("등록용 제목"))
                .andExpect(jsonPath("$.content").value("등록용 내용"))
                .andExpect(jsonPath("$.userId").value(postResponseDto.userId()))
                .andDo(document("post-get-one",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("게시글 단건 조회 실패")
    @Test
    void findByIdFail() throws Exception {
        //given
        when(postService.findById(any(Long.class))).thenThrow(new NotFoundException(ErrorCode.POST_NOT_FOUND));

        //when
        //then
        mockMvc.perform(get("/api/v1/posts/{id}", 11111L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.code").value("POST_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("게시물을 찾을 수 없습니다."))
                .andDo(document("post-get-one-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("timestamp"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("error").type(JsonFieldType.STRING).description("error"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message")
                        )
                ));
    }

    @DisplayName("게시글 수정 성공")
    @Test
    void update() throws Exception {
        //given
        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("수정용 제목")
                .content("수정용 내용")
                .build();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(1L)
                .title("수정용 제목")
                .content("수정용 내용")
                .userId(1L)
                .createdBy(null)
                .createdAt(LocalDateTime.now())
                .build();

        when(postService.update(any(Long.class), any(PostUpdateRequestDto.class))).thenReturn(postResponseDto);

        //when
        //then
        mockMvc.perform(put("/api/v1/posts/{id}", postResponseDto.id())
                        .content(objectMapper.writeValueAsString(postUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postResponseDto.id()))
                .andExpect(jsonPath("$.title").value("수정용 제목"))
                .andExpect(jsonPath("$.content").value("수정용 내용"))
                .andExpect(jsonPath("$.userId").value(postResponseDto.userId()))
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("게시글 수정 실패")
    @Test
    void updateFail() throws Exception {
        //given
        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("수정용 제목")
                .content(" ")
                .build();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .id(1L)
                .title("수정용 제목")
                .content(" ")
                .userId(1L)
                .createdBy(null)
                .createdAt(LocalDateTime.now())
                .build();

        when(postService.update(any(Long.class), any(PostUpdateRequestDto.class))).thenReturn(postResponseDto);

        //when
        //then
        mockMvc.perform(put("/api/v1/posts/{id}", postResponseDto.id())
                        .content(objectMapper.writeValueAsString(postUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value("INVALID_ENTITY_VALUE"))
                .andExpect(jsonPath("$.message").value("내용을 입력해주세요."))
                .andDo(document("post-update-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("timestamp"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("error").type(JsonFieldType.STRING).description("error"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message")
                        )
                ));
    }
}