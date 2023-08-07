package com.prgrms.boardjpa.Post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardjpa.Post.application.PostService;
import com.prgrms.boardjpa.Post.dto.request.PostCreateRequest;
import com.prgrms.boardjpa.Post.dto.request.PostUpdateRequest;
import com.prgrms.boardjpa.Post.dto.response.PostListResponse;
import com.prgrms.boardjpa.Post.dto.response.PostResponse;
import com.prgrms.boardjpa.User.application.UserService;
import com.prgrms.boardjpa.global.ErrorCode;
import com.prgrms.boardjpa.global.exception.BusinessServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(PostApiController.class)
class PostApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("게시물 등록 성공")
    void createPostSuccessTest() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .userId(1L)
                .title("제목")
                .content("내용")
                .build();
        PostResponse postResponse = new PostResponse(1L, "제목", "내용", 1L);

        given(postService.create(any()))
                .willReturn(postResponse);

        //when -> //then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-create",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(NUMBER).description("User Id"),
                                fieldWithPath("title").type(STRING).description("Title"),
                                fieldWithPath("content").type(STRING).description("Content")),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("게시판ID"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("content").type(STRING).description("내용"),
                                fieldWithPath("authorId").type(NUMBER).description("작성자ID"))
                ));
    }

    @Test
    @DisplayName("게시물 등록 실패 - 존재하지 않는 유저")
    void createPostFailTest_notFoundUser() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .userId(30L)
                .title("제목")
                .content("내용")
                .build();

        given(postService.create(any()))
                .willThrow(new BusinessServiceException(ErrorCode.NOT_FOUND_USER));

        //when -> then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(document("post-create-fail-not-found-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(NUMBER).description("User Id"),
                                fieldWithPath("title").type(STRING).description("Title"),
                                fieldWithPath("content").type(STRING).description("Content")),
                        responseFields(
                                fieldWithPath("httpStatus").type(STRING).description("상태 코드"),
                                fieldWithPath("message").type(STRING).description("응답 메세지"),
                                fieldWithPath("detailsMessage").type(OBJECT).description("상세 정보")
                        )));
    }

    @Test
    @DisplayName("게시물 등록 실패 - 잘못된 데이터 입력")
    void createPostFailTest_invalidData() throws Exception {

        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .userId(30L)
                .title(" ")
                .content("내용")
                .build();

        //when -> then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("post-create-fail-invalid-data",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(NUMBER).description("User Id"),
                                fieldWithPath("title").type(STRING).description("Title"),
                                fieldWithPath("content").type(STRING).description("Content")),
                        responseFields(
                                fieldWithPath("httpStatus").type(STRING).description("상태 코드"),
                                fieldWithPath("message").type(STRING).description("응답 메세지"),
                                subsectionWithPath("detailsMessage").description("상세 정보")
                                        .attributes(
                                                Attributes.key("title").value("상세 에러 메세지 - 제목을 입력해 주세요.")
                                        )
                        )));
    }

    @Test
    @DisplayName("게시물 전체 조회 성공")
    void findAllSuccessTest() throws Exception {

        //given
        PostListResponse response = new PostListResponse(List.of(new PostResponse(1L, "제목", "내용", 1L)));
        given(postService.findAll(any())).willReturn(response);

        //when -> then
        mockMvc.perform(get("/api/v1/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-all",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("postResponseList[].id").type(NUMBER).description("Post Id"),
                                fieldWithPath("postResponseList[].title").type(STRING).description("Title"),
                                fieldWithPath("postResponseList[].content").type(STRING).description("Content"),
                                fieldWithPath("postResponseList[].authorId").type(NUMBER).description("Author Id")
                        )));
    }

    @Test
    @DisplayName("게시물 단건 조회 성공")
    void findOneSuccessTest() throws Exception {

        //given
        PostResponse postResponse = new PostResponse(1L, "제목", "내용", 1L);
        given(postService.findOne(anyLong())).willReturn(postResponse);

        //when -> then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", postResponse.id())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-one",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Post Id")),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("게시물ID"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("content").type(STRING).description("내용"),
                                fieldWithPath("authorId").type(NUMBER).description("작성자ID")
                        )));
    }

    @Test
    @DisplayName("게시물 단건 조회 실패 - 존재하지 않는 게시물")
    void findOneFailTest_notFoundPost() throws Exception {

        long invalidId = 100L;
        given(postService.findOne(any())).willThrow(new BusinessServiceException(ErrorCode.NOT_FOUND_POST));

        //when -> then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", invalidId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(document("post-get-one-fail-not-found-post",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Post Id")),
                        responseFields(
                                fieldWithPath("httpStatus").type(STRING).description("상태 코드"),
                                fieldWithPath("message").type(STRING).description("응답 메세지"),
                                fieldWithPath("detailsMessage").type(OBJECT).description("상세 정보")
                        )));
    }

    @Test
    @DisplayName("게시물 수정 성공")
    void updatePostSuccessTest() throws Exception {

        //given
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("제목변경", "내용변경");
        PostResponse postResponse = new PostResponse(1L, "제목변경", "내용변경", 1L);
        given(postService.update(anyLong(), any())).willReturn(postResponse);

        //when -> then
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/posts/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Post Id")),
                        requestFields(
                                fieldWithPath("title").type(STRING).description("Update Title"),
                                fieldWithPath("content").type(STRING).description("Update Content")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("수정된 게시물 ID"),
                                fieldWithPath("title").type(STRING).description("수정된 제목"),
                                fieldWithPath("content").type(STRING).description("수정된 내용"),
                                fieldWithPath("authorId").type(NUMBER).description("작성자 ID")
                        )));
    }

    @Test
    @DisplayName("게시물 수정 실패 - 존재하지 않는 게시물")
    void updatePostFailTest_notFoundPost() throws Exception {

        //given
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("제목변경", "내용변경");

        given(postService.update(anyLong(), any())).willThrow(new BusinessServiceException(ErrorCode.NOT_FOUND_POST));
        //when -> then
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/posts/{id}", 100L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andDo(document("post-update-fail-not-found-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Post Id")),
                        requestFields(
                                fieldWithPath("title").type(STRING).description("Update Title"),
                                fieldWithPath("content").type(STRING).description("Update Content")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").type(STRING).description("상태 코드"),
                                fieldWithPath("message").type(STRING).description("응답 메세지"),
                                fieldWithPath("detailsMessage").type(OBJECT).description("상세 정보")
                        )));
    }

}