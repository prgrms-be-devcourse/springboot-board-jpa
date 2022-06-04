package com.kdt.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.springbootboardjpa.service.PostService;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.domain.dto.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.yml")
@Sql({"classpath:sql/schema.sql", "classpath:sql/data.sql"})
class RestPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService service;

    @Autowired
    private ObjectMapper objecctMapper;

    @Test
    @Transactional
    @DisplayName("ID를 통한 GET 테스트")
    void getPostByIdTest() throws Exception {

        //given
        var dto = service.findPost(1L);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", dto.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value(dto.getUsername()))
                .andExpect(jsonPath("title").value(dto.getTitle()))
                .andExpect(jsonPath("content").value(dto.getContent()))
                .andDo(print())
                .andDo(document("post-by-id",
                        pathParameters(
                                parameterWithName("id").description("post ID")
                        ),
                        responseFields(
                                fieldWithPath("postId").description("id"),
                                fieldWithPath("title").description("title"),
                                fieldWithPath("content").description("content"),
                                fieldWithPath("username").description("username")
                        )));
    }

    @Test
    @Transactional
    @DisplayName("Pageble을 이용한 GET 테스트")
    void getPostByPagebleTest() throws Exception {

        //given
        var pageble = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        var dto = service.findAllPosts(pageble);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "createdAt,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content[0].title").value(dto.getContent().get(0).getTitle()))
                .andDo(print())
                .andDo(document("post-pageble",
                        requestParameters(
                                parameterWithName("page").optional().description("page"),
                                parameterWithName("size").optional().description("size"),
                                parameterWithName("sort").optional().description("sort")
                        ),
                        responseBody()
                ));
    }

    @Test
    @Transactional
    @DisplayName("POST 테스트")
    void makePostTest() throws Exception {

        //given
        String body = objecctMapper.writeValueAsString(
                new PostCreateRequest("test-title", "--", "guest"));

        //then
        mockMvc.perform(post("/api/v1/posts")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("생성 성공")))
                .andDo(print())

                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("username").type(JsonFieldType.STRING).description("username")
                        ),
                        responseBody()
                ));
    }

    @Test
    @Transactional
    @DisplayName("게시글 수정 테스트")
    void editPostTest() throws Exception {

        //given
        var dto = service.findPost(1L);
        var request = new PostUpdateRequest("update-title", dto.getTitle());

        var body = objecctMapper.writeValueAsString(request);

        //then
        mockMvc.perform(post("/api/v1/posts/{id}", dto.getPostId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("수정 성공")))
                .andDo(print())

                .andDo(document("post-edit",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseBody()
                ));

    }

    @Test
    @Transactional
    @DisplayName("사용자 NotFound로 일어난 인가되지 않은 동작 예외 테스트 ")
    void unAuthorizationAccessExceptionTest() throws Exception {

        //given
        var request = new PostCreateRequest("access-exception", "==", "none");
        var body = objecctMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/posts")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("status").value(401))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("잘못된 인자 입력으로 일어난 PostNotFound 예외 테스트")
    void postNotFoundExceptionTest() throws Exception {

        //getPost
        Long requestId = 10L;
        mockMvc.perform(get("/api/v1/posts/{id}", requestId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("status").value(404))
                .andDo(print());

        //editPost
        var dto = service.findPost(1L);
        dto.setTitle("update-title-exeption");
        var body = objecctMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/posts/{id}", requestId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("status").value(404))
                .andDo(print());
    }

    @Test
    @DisplayName("유효성 검사 실패로 인한 MethodArgumentNotValid 예외 테스트")
    void methodArgumentNotValidExceptionTest() throws Exception{

        //create-validation
        var createRequest = new PostCreateRequest("validation-exception", "--", " ");
        var createBody = objecctMapper.writeValueAsString(createRequest);

        mockMvc.perform(post("/api/v1/posts")
                        .content(createBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("status").value(400))
                .andDo(print());

        //update-validation
        var updateRequest = new PostUpdateRequest("", "--");
        var updateBody = objecctMapper.writeValueAsString(updateRequest);

        mockMvc.perform(post("/api/v1/posts/{id}", 1L)
                        .content(updateBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("status").value(400))
                .andDo(print());
    }
}