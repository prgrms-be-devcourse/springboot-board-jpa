package com.example.jpaboard.post.controller;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.service.dto.FindAllRequest;
import com.example.jpaboard.post.service.dto.PostResponse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import org.springframework.restdocs.payload.JsonFieldType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;



    @Test
    void findAllBy() throws Exception {


        // API 호출 및 응답 문서화
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
    void updatePost() {
    }

    @Test
    void findById() {
    }

    @Test
    void savePost() {
    }
}
