package com.kdt.Board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.Board.EnableMockMvcEncodingUTF8;
import com.kdt.Board.dto.PostRequest;
import com.kdt.Board.dto.PostResponse;
import com.kdt.Board.dto.UserResponse;
import com.kdt.Board.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@EnableMockMvcEncodingUTF8
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class PostControllerTest {

    @MockBean private PostService postService;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    UserResponse userResponse = UserResponse.builder()
            .userId(1L)
            .name("유저1")
            .hobby("TV")
            .age(25)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .modifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();
    PostResponse postResponse = PostResponse.builder()
            .userResponse(userResponse)
            .id(1L)
            .title("제목1")
            .content("내용1")
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .modifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();

    @Test
    void 게시글_작성() throws Exception {
        //given
        PostRequest postRequest = new PostRequest("제목1", "내용1");
        given(postService.writePost(eq(1L), ArgumentMatchers.any(PostRequest.class))).willReturn(1L);

        //when
        ResultActions result = this.mockMvc.perform(
                post("/api/posts")
                        .content(objectMapper.writeValueAsString(postRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("userid", "1"))
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("write-post",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 설명"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("작성된 게시글 번호"),
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 번호"),
//                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
//                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
//                                fieldWithPath("userResponse").type(JsonFieldType.OBJECT).description("게시글 작성자"),
//                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 작성 시간"),
//                                fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정 시간")
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답시간"))
                        ));
    }

    @Test
    void 게시글_수정() throws Exception {
        //given
        PostRequest postRequest = new PostRequest("수정제목1", "수정내용1");
        given(postService.editPost(eq(1L), eq(1L), ArgumentMatchers.any(PostRequest.class))).willReturn(1L);

        //when
        ResultActions result = this.mockMvc.perform(
                put("/api/posts/{id}", 1L)
                        .content(objectMapper.writeValueAsString(postRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("userid", "1"))
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("edit-post",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 설명"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("수정된 게시글 번호"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답시간"))
                ));
    }

    @Test
    void 게시글_단건조회() throws Exception {
        //given
        given(postService.getPost(eq(1L))).willReturn(postResponse);

        //when
        ResultActions result = this.mockMvc.perform(
                get("/api/posts/{id}", 1L)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("get-post",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 설명"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 번호"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.userResponse").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.userResponse.userId").type(JsonFieldType.NUMBER).description("게시글 작성자 번호"),
                                fieldWithPath("data.userResponse.name").type(JsonFieldType.STRING).description("게시글 작성자 이름"),
                                fieldWithPath("data.userResponse.age").type(JsonFieldType.NUMBER).description("게시글 작성자 나이"),
                                fieldWithPath("data.userResponse.hobby").type(JsonFieldType.STRING).description("게시글 작성자 취미"),
                                fieldWithPath("data.userResponse.createdAt").type(JsonFieldType.STRING).description("게시글 작성자 회원가입 시간"),
                                fieldWithPath("data.userResponse.modifiedAt").type(JsonFieldType.STRING).description("게시글 작성자 마지막 회원정보 수정 시간"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 작성 시간"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("게시글 마지막 수정 시간"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답시간"))
                ));
    }

    @Test
    void 게시글_다건조회() throws Exception {
        //given
        final PageRequest pageRequest = PageRequest.of(0, 10);
        given(postService.getPosts(ArgumentMatchers.any(PageRequest.class))).willReturn(new PageImpl<>(List.of(postResponse), pageRequest, 10));

        //when
        ResultActions result = this.mockMvc.perform(
                get("/api/posts")
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("get-posts",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 설명"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("해당 페이지의 게시글 정보"),
                                fieldWithPath("data.content.[].id").type(JsonFieldType.NUMBER).description("게시글 번호"),
                                fieldWithPath("data.content.[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content.[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.content.[].userResponse").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.content.[].userResponse.userId").type(JsonFieldType.NUMBER).description("게시글 작성자 번호"),
                                fieldWithPath("data.content.[].userResponse.name").type(JsonFieldType.STRING).description("게시글 작성자 이름"),
                                fieldWithPath("data.content.[].userResponse.age").type(JsonFieldType.NUMBER).description("게시글 작성자 나이"),
                                fieldWithPath("data.content.[].userResponse.hobby").type(JsonFieldType.STRING).description("게시글 작성자 취미"),
                                fieldWithPath("data.content.[].userResponse.createdAt").type(JsonFieldType.STRING).description("게시글 작성자 회원가입 시간"),
                                fieldWithPath("data.content.[].userResponse.modifiedAt").type(JsonFieldType.STRING).description("게시글 작성자 마지막 회원정보 수정 시간"),
                                fieldWithPath("data.content.[].createdAt").type(JsonFieldType.STRING).description("게시글 작성 시간"),
                                fieldWithPath("data.content.[].modifiedAt").type(JsonFieldType.STRING).description("게시글 마지막 수정 시간"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이징 옵션 정보"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("페이징 옵션 - 정렬 정보"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬조건 존재 여부"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 되었는지 여부"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 안 되었는지 여부"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("해당 페이지의 첫 번째 원소의 수"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("한 페이지당 게시글 수"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 처리 했는지 여부"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 처리 안 했는지 여부"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지인지 여부"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("페이지 수에 따른 최대 게시글 수"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("페이지 내 최대 게시글 수"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("현재 페이지 정렬 설정"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("현재 페이지 정렬 기준 존재 여부"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("현재 페이지 정렬 여부"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("현재 페이지 비정렬 여부"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 번째 페이지인지 여부"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지에 있는 게시글 수"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("페이지에 게시글이 없는지 여부"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답시간"))
                ));
    }


}