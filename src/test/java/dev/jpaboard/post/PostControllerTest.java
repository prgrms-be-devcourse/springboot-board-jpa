package dev.jpaboard.post;

import com.epages.restdocs.apispec.Schema;
import dev.jpaboard.ControllerTest;
import dev.jpaboard.post.api.PostController;
import dev.jpaboard.post.application.PostService;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostResponse;
import dev.jpaboard.post.dto.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@ActiveProfiles("test")
public class PostControllerTest extends ControllerTest {

    @MockBean
    private PostService postService;

    @DisplayName("로그인한 유저는 게시물을 생성할 수 있다.")
    @Test
    void createPostTest() throws Exception {
        PostCreateRequest request = new PostCreateRequest("제목", "내용");
        given(postService.create(any(), any())).willReturn(new PostResponse("제목", "내용"));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1L);

        mockMvc.perform(post("/api/posts")
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andDo(document("post-create",
                                resourceDetails().tag("Post").description("게시물 생성")
                                        .requestSchema(Schema.schema("CreatePostRequest"))
                                        .responseSchema(Schema.schema("CreatePostResponse")),
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("JsessionID").description("세션").getAttributes()),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING)
                                                .description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("내용")
                                ),
                                responseFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING)
                                                .description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("내용")
                                )
                        )
                ).andExpect(status().isCreated());
    }

    @DisplayName("로그인한 유저는 자신의 게시물을 수정할 수 있다.")
    @Test
    void updatePostTest() throws Exception {

        PostUpdateRequest request = new PostUpdateRequest("제목", "내용");
        doNothing().when(postService).update(any(), any(), any());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1L);

        mockMvc.perform(patch("/api/posts/{id}", 1L)
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andDo(document("post-update",
                                resourceDetails().tag("Post").description("게시물 업데이트")
                                        .requestSchema(Schema.schema("UpdatePostRequest")),
                                pathParameters(parameterWithName("id").description("Post ID")),
                                requestHeaders(
                                        headerWithName("JsessionID").description("세션").getAttributes()
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용")
                                )
                        )
                ).andExpect(status().isNoContent());
    }

    @DisplayName("로그인한 유저는 자신의 게시물을 삭제할 수 있다.")
    @Test
    void deletePostTest() throws Exception {
        doNothing().when(postService).delete(any(), any());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1L);

        mockMvc.perform(delete("/api/posts/{id}", 1L)
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("post-delete",
                                resourceDetails().tag("Post").description("게시물 삭제"),
                                pathParameters(parameterWithName("id").description("Post ID")),
                                requestHeaders(
                                        headerWithName("JsessionID").description("세션").getAttributes()
                                )
                        )
                ).andExpect(status().isOk());
    }

    @DisplayName("게시물 아이디로 게시물을 조회할 수 있다.")
    @Test
    void findPostTest() throws Exception {
        given(postService.findPost(any())).willReturn(new PostResponse("제목", "내용"));

        mockMvc.perform(get("/api/posts/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("post-find",
                                resourceDetails().tag("Post").description("게시물 조회")
                                        .responseSchema(Schema.schema("FindPostResponse")),
                                pathParameters(parameterWithName("id").description("Post ID")),
                                responseFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING)
                                                .description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("내용")
                                )
                        )
                ).andExpect(status().isOk());
    }

}
