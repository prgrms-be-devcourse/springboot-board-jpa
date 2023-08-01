package org.prgms.springbootBoardJpa.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootBoardJpa.service.PostCreateRequest;
import org.prgms.springbootBoardJpa.service.PostResponse;
import org.prgms.springbootBoardJpa.service.PostService;
import org.prgms.springbootBoardJpa.service.PostUpdateRequest;
import org.prgms.springbootBoardJpa.service.UserInfo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest extends RestDocsSupport {

    @MockBean
    private PostService postService;

    @Override
    protected Object initController() {
        return new PostController(postService);
    }

    @Test
    @DisplayName("게시글을 생성한다.")
    void 게시글_생성() throws Exception {
        //given
        var request = new PostCreateRequest(new UserInfo("testName", 13, "축구"), "testTitle", "testContent");
        doNothing().when(postService).create(any(PostCreateRequest.class));

        //when && then
        mockMvc.perform(post("/api/v1/posts")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("post-create",
                preprocessRequest(prettyPrint()),
                requestFields(
                    fieldWithPath("userInfo.name").type(JsonFieldType.STRING).description("작성자 이름"),
                    fieldWithPath("userInfo.age").type(JsonFieldType.NUMBER).description("작성자 나이"),
                    fieldWithPath("userInfo.hobby").type(JsonFieldType.STRING).description("작성자 취미"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("컨텐츠")
                )
            ));
    }

    @Test
    @DisplayName("게시글을 수정한다.")
    void 게시글_수정() throws Exception {
        // given
        var id = 1L;
        var request = new PostUpdateRequest("testTitle", "testContent");
        doNothing().when(postService).update(any(Long.class), any(PostUpdateRequest.class));

        // when && then
        mockMvc.perform(post("/api/v1/posts/{id}", id)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("post-update",
                preprocessRequest(prettyPrint()),
                pathParameters(parameterWithName("id").description("게시물 아이디")),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                )
            ));
    }

    @Test
    @DisplayName("게시글을 모두 조회한다.")
    void 게시글_모두_조회() throws Exception {
        // given
        var response = List.of(
                new PostResponse(1L, "testTitle1", "testContent1"),
                new PostResponse(2L, "testTitle2", "testContent2")
            );
        given(postService.findAll(any(Pageable.class))).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/posts"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("testTitle1")))
            .andExpect(jsonPath("$[0].content", is("testContent1")))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].title", is("testTitle2")))
            .andExpect(jsonPath("$[1].content", is("testContent2")))
            .andDo(document("post-find-all",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                    fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용")
                )
            ));
    }

    @Test
    @DisplayName("아이디로 게시글을 조회한다.")
    void 아이디로_게시글_조회() throws Exception {
        //given
        var id = 1L;
        var response = new PostResponse(id, "testTitle", "testContent");
        given(postService.findOne(any(Long.class))).willReturn(response);

        //when & then
        mockMvc.perform(get("/api/v1/posts/{id}", id))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("testTitle")))
            .andExpect(jsonPath("$.content", is("testContent")))
            .andDo(document("post-find-one",
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("id").description("게시물 아이디")),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                )
            ));
    }

}