package com.example.board.controller;

import com.example.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.board.dto.PostDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest({PostRestController.class})
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveTest() throws Exception {
        Request request = Request.builder()
                .userId(1L)
                .title("title")
                .content("content")
                .build();

        Response response = Response.builder()
                .userId(1L)
                .title("title")
                .content("content")
                .createdAt(LocalDateTime.now())
                .build();

        given(service.save(request)).willReturn(response);
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("fail").type(JsonFieldType.BOOLEAN).description("실패여부"),
                                fieldWithPath("msg").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").description("응답데이터"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간")
                        )
                ));
    }

    @Test
    void findAllTest() throws Exception {
        Request request = Request.builder()
                .userId(1L)
                .title("title")
                .content("content")
                .build();
        service.save(request);

        Response response = Response.builder()
                .userId(1L)
                .title("title")
                .content("content")
                .createdAt(LocalDateTime.now())
                .build();
        List<Response> responses = new ArrayList<>();
        responses.add(response);

        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<Response> pageResponse = new PageImpl<>(responses, pageable, responses.size());

        given(service.findAll(PageRequest.of(0, 10))).willReturn(pageResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("post-findAll",
                        responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("fail").type(JsonFieldType.BOOLEAN).description("실패여부"),
                                fieldWithPath("msg").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.content[].userId").description("작성자 ID"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 개수"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("공백 여부"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 O"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 X"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("오프셋"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이지 O"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이지 X"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 여부"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 개수"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("처음 여부"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("크기"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("번호"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 공백 여부"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부(O)"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 여부(X)"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("데이터 개수"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("공백 여부")
                        )
                ));
    }

    @Test
    void findByIdTest() throws Exception {
        Response response = Response.builder()
                .userId(1L)
                .title("title")
                .content("content")
                .createdAt(LocalDateTime.now())
                .build();

        given(service.findById(1L)).willReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("post-findById",
                        responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("fail").type(JsonFieldType.BOOLEAN).description("실패여부"),
                                fieldWithPath("msg").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data").description("응답데이터"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간")
                        )
                ));
    }

    @Test
    void updateTest() throws Exception {
        Request request = Request.builder()
                .userId(1L)
                .title("title")
                .content("content")
                .build();
        service.save(request);

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/{id}", 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        )
                ));
    }
}