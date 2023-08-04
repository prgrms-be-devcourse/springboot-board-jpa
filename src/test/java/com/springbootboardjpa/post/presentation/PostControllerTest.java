package com.springbootboardjpa.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootboardjpa.post.application.PostService;
import com.springbootboardjpa.post.dto.PostRequest;
import com.springbootboardjpa.post.dto.PostResponse;
import com.springbootboardjpa.post.dto.PostWithoutContentResponse;
import com.springbootboardjpa.post.dto.PostsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    void 게시판을_저장한다() throws Exception {
        // given
        PostRequest request = new PostRequest(1L, "안녕하세요", "자기소개");
        PostResponse response = new PostResponse(1L, 1L, "안녕하세요", "자기소개");

        given(postService.save(any())).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andDo(document("post/save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시판 ID"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목")
                        )))
                .andExpect(status().isCreated());
    }
    
    @Test
    void 테스트를_아이디로_조회한다() throws Exception {
        // given
        Long id = 1L;
        PostResponse response = new PostResponse(1L, 1L, "안녕하세요", "자기소개");

        given(postService.findById(any())).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/posts/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("post/select",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시판 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시판 ID"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시판 내용"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시판 제목")
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void 게시판을_모두_조회한다() throws Exception {
        // given
        PostsResponse response = new PostsResponse(List.of(new PostWithoutContentResponse(1L, 1L, "자기소개")));

        given(postService.findAll()).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("post/select",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("posts[].id").type(JsonFieldType.NUMBER).description("게시판 ID"),
                                fieldWithPath("posts[].memberId").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("posts[].tittle").type(JsonFieldType.STRING).description("게시판 제목")
                        )))
                .andExpect(status().isOk());
    }
}
