package com.prgrms.jpaboard.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.jpaboard.domain.post.dto.request.PostCreateDto;
import com.prgrms.jpaboard.domain.post.dto.request.PostUpdateDto;
import com.prgrms.jpaboard.global.common.resquest.PageParamDto;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글 생성 테스트 및 문서화")
    void testCreatePostCall() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto(1L, "테스트 중 입니다.", "같은 내용의 문서가 여러개 생기더라도 신경쓰지 마세요.");

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("응답 결과"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("생성된 게시글의 ID"),
                                fieldWithPath("result.createdAt").type(JsonFieldType.STRING).description("게시글의 생성시간")
                        )

                ));
    }

    @Test
    @DisplayName("게시글 페이징 조회 테스트 및 문서화")
    void testGetPostsCall() throws Exception {
        PageParamDto pageParamDto = new PageParamDto(0, 10);

        mockMvc.perform(get("/api/v1/posts")
                .param("page", "0")
                .param("perPage", "10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-paging-get",
                        requestParameters(
                                parameterWithName("page").description("현재 페이지"),
                                parameterWithName("perPage").description("한 페이지 당 게시글 개수")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("응답 결과"),
                                fieldWithPath("result.metadata").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("result.metadata.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("result.metadata.totalPage").type(JsonFieldType.NUMBER).description("총 페이지 개수"),
                                fieldWithPath("result.metadata.perPage").type(JsonFieldType.NUMBER).description("한 페이지 당 게시글 개수"),
                                fieldWithPath("result.metadata.total").type(JsonFieldType.NUMBER).description("총 게시글 개수"),
                                fieldWithPath("result.posts[]").type(JsonFieldType.ARRAY).description("게시글 목록"),
                                fieldWithPath("result.posts[].id").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("result.posts[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("result.posts[].user").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("result.posts[].user.id").type(JsonFieldType.NUMBER).description("게시글 작성자 ID"),
                                fieldWithPath("result.posts[].user.name").type(JsonFieldType.STRING).description("게시글 작성자 이름"),
                                fieldWithPath("result.posts[].createdAt").type(JsonFieldType.STRING).description("게시글 생성시간")
                        )
                ));
    }


    @Test
    @DisplayName("게시글 조회 테스트 및 문서화")
    void testGetPost() throws Exception {
        PageParamDto pageParamDto = new PageParamDto(0, 10);

        mockMvc.perform(get("/api/v1/posts/11"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("응답 결과"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("result.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("result.content").type(JsonFieldType.STRING).description("게시글 본뮨"),
                                fieldWithPath("result.createdAt").type(JsonFieldType.STRING).description("게시글 생성시간"),
                                fieldWithPath("result.user").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("result.user.id").type(JsonFieldType.NUMBER).description("게시글 작성자 ID"),
                                fieldWithPath("result.user.name").type(JsonFieldType.STRING).description("게시글 작성자 이름")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 생성 테스트 및 문서화")
    void testUpdatePostCall() throws Exception {
        PostUpdateDto postUpdateDto = new PostUpdateDto("수정 테스트 중인 게시글", "게시글 수정을 테스트하고 있습니다.");

        mockMvc.perform(put("/api/v1/posts/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("result").type(JsonFieldType.OBJECT).description("응답 결과"),
                                fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("수정된 게시글의 ID"),
                                fieldWithPath("result.updatedAt").type(JsonFieldType.STRING).description("게시글의 수정시간")
                        )

                ));
    }
}