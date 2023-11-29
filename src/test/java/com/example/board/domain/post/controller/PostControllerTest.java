package com.example.board.domain.post.controller;


import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    void 게시글_저장_호출_테스트() throws Exception {
        // Given
        String email = "test@gmail.com";
        PostCreateRequest request = new PostCreateRequest("제목1", "내용1");
        PostResponse response = new PostResponse(
                1L,
                "제목1",
                "내용1",
                0,
                "홍길동",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );

        given(postService.createPost(email, request)).willReturn(response);

        // When & Then
        mvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", email)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("view").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("게시글 작성자"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 수정 시간")
                        )
                ));
    }

    @Test
    void 게시글_아이디로_조회_호출_테스트() throws Exception {
        // Given
        Long id = 1L;
        PostResponse response = new PostResponse(
                1L,
                "제목1",
                "내용1",
                0,
                "홍길동",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );

        given(postService.findPostById(id)).willReturn(response);

        // When & Then
        mvc.perform(get("/api/v1/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findById",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("view").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("게시글 작성자"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 수정 시간")
                        )
                ));
    }

    @Test
    void 게시글_전체_조회_호출_테스트() throws Exception {
        // Given
        List<PostResponse> posts = List.of(new PostResponse(
                1L,
                "제목1",
                "내용1",
                0,
                "홍길동",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        ));
        PageRequest pageable = PageRequest.of(0, 10);
        Page<PostResponse> pageResponse = PageableExecutionUtils.getPage(posts, pageable, () -> posts.size());
        given(postService.findPostsByCondition(any(PostPageCondition.class))).willReturn(pageResponse);

        // When & Then
        mvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findAll",
                        responseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("content[].view").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                fieldWithPath("content[].name").type(JsonFieldType.STRING).description("게시글 작성자"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간"),
                                fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("게시글 수정 시간"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 당 개수"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 필드가 비어 있는지 여부"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬이 적용된 상태인지 여부"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬이 적용되지 않은 상태인지 여부"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("페이지 오프셋 (시작 인덱스)"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징이 적용된 상태인지 여부"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징이 적용되지 않은 상태인지 여부"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 개수"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 필드가 비어 있는지 여부"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("현재 정렬된 상태인지 여부"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("현재 정렬되지 않은 상태인지 여부"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 데이터 개수"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("응답이 비어 있는지 여부")
                        )
                ));
    }

    @Test
    void 게시글_수정_호출_테스트() throws Exception {
        // Given
        Long id = 1L;
        String email = "test@gmail.com";
        PostUpdateRequest request = new PostUpdateRequest("제목 수정", "내용 수정");
        PostResponse response = new PostResponse(
                id,
                request.title(),
                request.content(),
                0,
                "홍길동",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );

        given(postService.updatePost(id, email, request)).willReturn(response);

        // When & Then
        mvc.perform(patch("/api/v1/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", email)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("view").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("게시글 작성자"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 수정 시간")
                        )
                ));
    }

    @Test
    void 게시글_아이디로_삭제_호출_테스트() throws Exception {
        // Given
        Long id = 1L;
        String email = "test@gmail.com";

        // When & Then
        mvc.perform(delete("/api/v1/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", email))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("post-deleteById"));
    }

    @Test
    void 게시글_전체_삭제_호출_테스트() throws Exception{
        // Given

        // When & Then
        mvc.perform(delete("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("post-deleteAll"));
    }
}
