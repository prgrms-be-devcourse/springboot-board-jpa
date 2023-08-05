package com.example.jpaboard.post.controller;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.post.controller.dto.SaveApiRequest;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.service.dto.FindAllRequest;
import com.example.jpaboard.post.service.dto.PostResponse;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllBy() throws Exception {

        this.mockMvc.perform(get("/posts")
                        .param("title", "Your Title")
                        .param("content", "Your Content"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findAllBy",
                        responseFields(
                                subsectionWithPath("result").description("Result details"),
                                fieldWithPath("result.content[]").description("The list of content items"),
                                subsectionWithPath("result.pageable").description("Pagination information"),
                                fieldWithPath("result.pageable.sort.empty").description("Indicates if the sort is empty"),
                                fieldWithPath("result.pageable.sort.sorted").description("Indicates if the sort is sorted"),
                                fieldWithPath("result.pageable.sort.unsorted").description("Indicates if the sort is unsorted"),
                                fieldWithPath("result.pageable.offset").description("Offset value for pagination"),
                                fieldWithPath("result.pageable.pageSize").description("Page size for pagination"),
                                fieldWithPath("result.pageable.pageNumber").description("Page number"),
                                fieldWithPath("result.pageable.unpaged").description("Indicates if the page is unpaged"),
                                fieldWithPath("result.pageable.paged").description("Indicates if the page is paged"),
                                fieldWithPath("result.size").description("The page size"),
                                fieldWithPath("result.number").description("The current page number"),
                                fieldWithPath("result.sort.empty").description("Indicates if the sort is empty"),
                                fieldWithPath("result.sort.sorted").description("Indicates if the sort is sorted"),
                                fieldWithPath("result.sort.unsorted").description("Indicates if the sort is unsorted"),
                                fieldWithPath("result.first").description("Indicates if this is the first page"),
                                fieldWithPath("result.last").description("Indicates if this is the last page"),
                                fieldWithPath("result.numberOfElements").description("Number of elements in the current page"),
                                fieldWithPath("result.empty").description("Indicates if the content is empty"),
                                fieldWithPath("resultCode").description("The result code of the response"),
                                fieldWithPath("resultMsg").description("The result message of the response")
                        )
                ));
    }

    @Test
    public void updatePost() throws Exception {
        // Request 바디 필드 설명을 작성합니다.
        FieldDescriptor[] requestFields = new FieldDescriptor[] {
                fieldWithPath("title").description("Updated title of the post"),
                fieldWithPath("content").description("Updated content of the post"),
                fieldWithPath("memberId").description("ID of the member making the update")
        };

        // Response 바디 필드 설명을 작성합니다.
        FieldDescriptor[] responseFields = new FieldDescriptor[] {
                fieldWithPath("result.postId").description("The id of the updated post"),
                fieldWithPath("result.title").description("The title of the updated post"),
                fieldWithPath("result.content").description("The content of the updated post"),
                fieldWithPath("result.memberName").description("The name of the member who made the update"),
                fieldWithPath("resultCode").description("The result code of the response"),
                fieldWithPath("resultMsg").description("The result message of the response")
        };

        // API 호출 및 문서 생성
        this.mockMvc.perform(
                        patch("/posts/{id}", 1L)  // 실제 ID 값으로 대체해주세요
                                .content("{\"title\": \"흑구팀 화이팅팅\", \"content\": \"언제나 응원해 흑구팀팀\", \"memberId\": 1}")  // 업데이트할 내용 전달
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        requestFields(requestFields),
                        responseFields(responseFields)
                ));
    }

    @Test
    void findById() throws Exception {

        // Response 바디 필드 설명을 작성합니다.
        FieldDescriptor[] responseFields = new FieldDescriptor[] {
                fieldWithPath("result.postId").description("The id of the detailed post"),
                fieldWithPath("result.title").description("The title of the detailed post"),
                fieldWithPath("result.content").description("The content of the detailed post"),
                fieldWithPath("result.memberName").description("The name of writer"),
                fieldWithPath("resultCode").description("The result code of the response"),
                fieldWithPath("resultMsg").description("The result message of the response")
        };

        // API 호출 및 문서 생성
        this.mockMvc.perform(
                        get("/posts/{id}", 1L)  // 실제 ID 값으로 대체해주세요
                )
                .andExpect(status().isOk())
                .andDo(document("post-findById",
                        responseFields(responseFields)
                ));
    }

    @Test
    void savePost() throws Exception {
        // Request 바디 필드 설명을 작성합니다.
        FieldDescriptor[] requestFields = new FieldDescriptor[] {
                fieldWithPath("title").description("Saved title of the post"),
                fieldWithPath("content").description("Saved content of the post"),
                fieldWithPath("memberId").description("ID of the member making the post")
        };

        // Response 바디 필드 설명을 작성합니다.
        FieldDescriptor[] responseFields = new FieldDescriptor[] {
                fieldWithPath("postId").description("The id of the saved post"),
                fieldWithPath("title").description("The title of the saved post"),
                fieldWithPath("content").description("The content of the saved post"),
                fieldWithPath("memberName").description("The name of writer"),
        };

        SaveApiRequest saveApiRequest = new SaveApiRequest(1L,"흑구영수팀 화이팅","흑구영수팀팀팀");

        this.mockMvc.perform(
                post("/posts")
                        .content(objectMapper.writeValueAsString(saveApiRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("post-save",
                        requestFields(requestFields),
                        responseFields(responseFields)
                ));

    }
}
