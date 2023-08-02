package dev.jpaboard.post;

import dev.jpaboard.ControllerTest;
import dev.jpaboard.post.api.PostController;
import dev.jpaboard.post.application.PostService;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostResponse;
import dev.jpaboard.post.dto.PostUpdateRequest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@ActiveProfiles("test")
public class PostControllerTest extends ControllerTest {

    @MockBean
    PostService postService;

    @Test
    void create() throws Exception {
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
                                resourceDetails().description("게시물 생성"),
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

    @Test
    void update() throws Exception {

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
                                resourceDetails().description("게시물 업데이트"),
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

    @Test
    void deletePost() throws Exception {
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
                                resourceDetails().description("게시물 삭제"),
                                pathParameters(parameterWithName("id").description("Post ID")),
                                requestHeaders(
                                        headerWithName("JsessionID").description("세션").getAttributes()
                                )
                        )
                ).andExpect(status().isOk());

    }

    @Test
    void findPost() throws Exception {
        given(postService.findPost(any())).willReturn(new PostResponse("제목", "내용"));

        mockMvc.perform(get("/api/posts/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("post-find",
                                resourceDetails().description("게시물 조회"),
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
