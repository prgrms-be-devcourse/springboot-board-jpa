package org.prgms.boardservice.domain.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.boardservice.domain.post.dto.PostCreateRequestDto;
import org.prgms.boardservice.domain.post.dto.PostUpdateRequestDto;
import org.prgms.boardservice.domain.post.vo.PostUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글이 성공적으로 생성된다.")
    void success_Save_Post() throws Exception {
        PostCreateRequestDto requestDto = new PostCreateRequestDto("title", "content", 1L);

        // given
        given(postService.create(any(Post.class))).willReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/posts/1"))
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 id")
                        ),
                        responseHeaders(
                                headerWithName("location").description("리소스 위치")
                        ))
                );
    }

    @Test
    @DisplayName("게시글을 id로 조회할 수 있다.")
    void success_Get_One_Post() throws Exception {
        // given
        Long postId = 1L;
        Post post = new Post(postId, new Title("title"), new Content("content"), 1L);

        given(postService.getById(postId)).willReturn(post);

        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", postId));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(post.getId()))
                .andExpect(jsonPath("title").value(post.getTitle()))
                .andExpect(jsonPath("content").value(post.getContent()))
                .andExpect(jsonPath("userId").value(post.getUserId()))
                .andDo(print())
                .andDo(document("post-get-one",
                        pathParameters(
                                parameterWithName("id").description("게시글 id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 id")
                        ))
                );
    }

    @Test
    @DisplayName("게시글을 페이지로 조회할 수 있다.")
    void success_Get_Page_Post() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id"));

        Post post1 = new Post(1L, new Title("title1"), new Content("content1"), 1L);
        Post post2 = new Post(2L, new Title("title2"), new Content("content2"), 1L);

        Page<Post> posts = new PageImpl<>(List.of(post2, post1), pageRequest, 3);

        given(postService.getByPage(pageRequest)).willReturn(posts);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(2))
                .param("sort", "id,desc"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(post2.getId()))
                .andExpect(jsonPath("$.data[1].id").value(post1.getId()))
                .andExpect(jsonPath("$.pageable.first").value(true))
                .andExpect(jsonPath("$.pageable.last").value(false))
                .andExpect(jsonPath("$.pageable.number").value(0))
                .andExpect(jsonPath("$.pageable.size").value(2))
                .andExpect(jsonPath("$.pageable.totalPages").value(2))
                .andExpect(jsonPath("$.pageable.totalElements").value(3))
                .andExpect(jsonPath("$.pageable.sort").value("id: DESC"))
                .andDo(print())
                .andDo(document("post-get-by-page",
                        responseFields(
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("pageable.first").type(JsonFieldType.BOOLEAN).description("처음 페이지 여부"),
                                fieldWithPath("pageable.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("pageable.number").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("pageable.size").type(JsonFieldType.NUMBER).description("페이지 당 게시글 개수"),
                                fieldWithPath("pageable.sort").type(JsonFieldType.STRING).description("정렬 기준"),
                                fieldWithPath("pageable.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("pageable.totalElements").type(JsonFieldType.NUMBER).description("전체 게시글 수")
                        ))
                );
    }

    @Test
    @DisplayName("게시글이 성공적으로 수정된다.")
    void success_Update_Post() throws Exception {
        Long postId = 1L;
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto("new-title", "new-content");

        // given
        given(postService.update(any(PostUpdateVo.class))).willReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/v1/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        resultActions
                .andExpect(status().isNoContent())
                .andExpect(header().stringValues("Location", "/api/v1/posts/1"))
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseHeaders(
                                headerWithName("location").description("리소스 위치")
                        ))
                );
    }

    @Test
    @DisplayName("게시글을 id로 삭제할 수 있다.")
    void success_Delete_Post() throws Exception {
        // given
        Long postId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/posts/{id}", postId));

        // then
        resultActions
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("post-delete",
                        pathParameters(
                                parameterWithName("id").description("게시글 id")
                        )
                ));
    }
}
