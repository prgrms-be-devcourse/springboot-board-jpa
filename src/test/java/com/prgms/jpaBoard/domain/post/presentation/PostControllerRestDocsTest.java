package com.prgms.jpaBoard.domain.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgms.jpaBoard.domain.post.application.PostService;
import com.prgms.jpaBoard.domain.post.application.dto.PostResponse;
import com.prgms.jpaBoard.domain.post.application.dto.PostResponses;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostSaveRequest;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(PostController.class)
@ExtendWith(RestDocumentationExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
public class PostControllerRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .operationPreprocessors()
                        .withRequestDefaults(modifyUris().host("localhost").port(8080), prettyPrint())
                        .withResponseDefaults(modifyUris().host("localhost").port(8080), prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("게시글을 생성 할 수 있다.")
    void createPost() throws Exception {
        // Given
        PostSaveRequest postSaveRequest = new PostSaveRequest("제목", "내용", 1L);

        given(postService.save(postSaveRequest)).willReturn(1L);

        // When

        // Then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postSaveRequest))
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andDo(document("create-post", resource("Create a Post"),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버 시간")
                        )));
    }

    @Test
    @DisplayName("게시글 하나를 조회할 수 있다.")
    void readPost() throws Exception {
        // Given
        PostResponse postResponse = new PostResponse(
                1L,
                "title",
                "content",
                LocalDateTime.now(),
                "Writer");

        given(postService.readOne(1L)).willReturn(postResponse);

        // When


        // Then
        mockMvc.perform(get("/api/v1/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andDo(document("read-post", resource("read a post"),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버 시간")
                        )));
    }

    @Test
    @DisplayName("게시글 전체를 조회할 수 있다.")
    void readPosts() throws Exception {
        // Given
        PostResponse postResponseA = new PostResponse(
                1L,
                "titleA",
                "contentA",
                LocalDateTime.now(),
                "Writer");

        PostResponse postResponseB = new PostResponse(
                2L,
                "titleB",
                "contentB",
                LocalDateTime.now(),
                "Writer");

        PostResponse postResponseC = new PostResponse(
                3L,
                "titleC",
                "contentC",
                LocalDateTime.now(),
                "Writer");

        PostResponse postResponseD = new PostResponse(
                4L,
                "titleD",
                "contentD",
                LocalDateTime.now(),
                "Writer");

        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));

        given(postService.readAll(pageRequest))
                .willReturn(new PostResponses(List.of(postResponseA, postResponseB, postResponseC, postResponseD)));

        // When


        // Then
        mockMvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "id,desc"))
                .andDo(print())
                .andDo(document("read-posts", resource("read All Posts"),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER)
                                        .description("상태코드"),
                                fieldWithPath("data.postResponses[].id").type(JsonFieldType.NUMBER)
                                        .description("게시글 ID"),
                                fieldWithPath("data.postResponses[].title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("data.postResponses[].content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("data.postResponses[].createdAt").type(JsonFieldType.STRING)
                                        .description("작성 시간"),
                                fieldWithPath("data.postResponses[].createdBy").type(JsonFieldType.STRING)
                                        .description("작성자 이름"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING)
                                        .description("서버 시간")
                        )));
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다.")
    void updatePost() throws Exception {
        // Given
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("ModifiedTitle", "ModifiedContent");

        PostResponse postResponse = new PostResponse(
                1L,
                "ModifiedTitle",
                "ModifiedContent",
                LocalDateTime.now(),
                "Writer"
        );

        given(postService.update(1L, postUpdateRequest)).willReturn(postResponse);

        // When

        // Then
        mockMvc.perform(post("/api/v1/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest))
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andDo(document("update-post", resource("update Post"),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버 시간")
                        )));
    }
}
