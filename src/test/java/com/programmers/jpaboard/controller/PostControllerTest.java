package com.programmers.jpaboard.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpaboard.AbstractRestDocsTest;
import com.programmers.jpaboard.dto.post.request.PostCreateRequest;
import com.programmers.jpaboard.dto.post.request.PostUpdateRequest;
import com.programmers.jpaboard.dto.user.request.UserCreateRequest;
import com.programmers.jpaboard.service.PostService;
import com.programmers.jpaboard.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class PostControllerTest extends AbstractRestDocsTest {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PostControllerTest(RestDocumentationResultHandler resultHandler, MockMvc mockMvc, ObjectMapper objectMapper,
            UserService userService, PostService postService) {
        super(resultHandler, mockMvc);
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.postService = postService;
    }

    @BeforeAll
    void postSetUp() {
        UserCreateRequest userRequest = new UserCreateRequest("kyle kim", 29, "basketball");
        userService.createUser(userRequest);

        PostCreateRequest postRequest1 = new PostCreateRequest("test title1", "test content1", 1L);
        PostCreateRequest postRequest2 = new PostCreateRequest("test title2", "test content2", 1L);
        postService.createPost(postRequest1);
        postService.createPost(postRequest2);
    }

    @Test
    @DisplayName("[RestDocs] CREATE Post")
    @Transactional
    void createPostTest() throws Exception {
        // given
        PostCreateRequest request = new PostCreateRequest("my title", "my content", 1L);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(document("CREATE Post",
                        resourceDetails().description("게시물 생성"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.NULL).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시물 ID")
                        )
                ));
    }

    @Test
    @DisplayName("[RestDocs] READ ALL Posts")
    void findAllPostsTest() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/posts"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("READ ALL Posts",
                        resourceDetails().description("전체 게시물 조회"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.NULL).description("응답 메시지"),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                fieldWithPath("data[].postId").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("게시물 작성일시"),
                                fieldWithPath("data[].createdBy").type(JsonFieldType.STRING).description("게시물 작성자"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("작성자 ID")
                        )
                ));
    }

    @Test
    @DisplayName("[RestDocs] READ Post")
    void findPostByIdTest() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/posts/{id}", id));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("READ Post",
                        resourceDetails().description("상세 게시물 조회"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("조회할 게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.NULL).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시물 작성일시"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("게시물 작성자"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 ID")
                        )
                ));
    }

    @Test
    @DisplayName("[RestDocs] UPDATE Post")
    @Transactional
    void updatePostTest() throws Exception {
        // given
        Long id = 1L;
        PostUpdateRequest request = new PostUpdateRequest("updated title", "updated content");

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/v1/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("UPDATE Post",
                        resourceDetails().description("게시물 수정"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("수정할 게시물 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 게시물 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.NULL).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("수정된 게시물 ID")
                        )
                ));
    }

    @Test
    @DisplayName("[RestDocs] DELETE Post")
    @Transactional
    void deletePostTest() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/posts/{id}", id));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("DELETE Post",
                        resourceDetails().description("게시물 삭제"),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("삭제할 게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.NULL).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                        )
                ));
    }
}