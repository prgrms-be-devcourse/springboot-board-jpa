package com.example.board.domain.post.controller;


import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.service.PostService;
import com.example.board.global.security.config.SecurityConfig;
import com.example.board.global.security.details.MemberDetails;
import com.example.board.global.security.jwt.filter.JwtFilter;
import com.example.board.global.security.jwt.provider.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.board.factory.post.PostFactory.createPost;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = PostController.class,
    excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class),
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtTokenProvider.class)
    }
)
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    private final Post post = createPost();
    private final Member member = post.getMember();

    @Test
    @WithMockUser
    void 게시글_저장_호출_테스트() throws Exception {
        // Given
        PostCreateRequest request = new PostCreateRequest(post.getTitle(), post.getContent());
        PostResponse response = new PostResponse(
                1L,
                post.getTitle(),
                post.getContent(),
                0,
                member.getName(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                member.getName()
        );

        long expectedUserId = 1;
        MemberDetails memberDetails = new MemberDetails(Long.toString(expectedUserId),
                "username",
                "password",
                List.of());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(memberDetails,"", memberDetails.getAuthorities()));

        given(postService.createPost(anyLong(), any(PostCreateRequest.class))).willReturn(response);

        // When & Then
        mvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
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
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 수정 시간"),
                                fieldWithPath("updatedBy").type(JsonFieldType.STRING).description("게시글 수정자")
                        )
                ));
    }

    @Test
    void 게시글_아이디로_조회_호출_테스트() throws Exception {
        // Given
        Long id = 1L;
        PostResponse response = new PostResponse(
                1L,
                post.getTitle(),
                post.getContent(),
                0,
                member.getName(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                member.getName()
        );

        given(postService.findPostByIdAndUpdateView(id)).willReturn(response);

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
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 수정 시간"),
                                fieldWithPath("updatedBy").type(JsonFieldType.STRING).description("게시글 수정자")
                        )
                ));
    }

    @Test
    void 게시글_전체_조회_호출_테스트() throws Exception {
        // Given
        List<PostResponse> posts = List.of(new PostResponse(
                1L,
                post.getTitle(),
                post.getContent(),
                0,
                member.getName(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                member.getName()
        ));
        PageRequest pageable = PageRequest.of(0, 10);
        Page<PostResponse> pageResponse = PageableExecutionUtils.getPage(posts, pageable, posts::size);
        given(postService.findPostsByCondition(any(PostPageCondition.class))).willReturn(pageResponse);

        // When & Then
        mvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findAll",
                        responseFields(
                                fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("startIndex").type(JsonFieldType.NUMBER).description("시작 인덱스"),
                                fieldWithPath("endIndex").type(JsonFieldType.NUMBER).description("끝 인덱스"),
                                fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN).description("이전 페이지 존재 유무"),
                                fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 유무"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 데이터 개수"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].view").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("게시글 작성자 이름"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("게시글 작성 날짜"),
                                fieldWithPath("data[].updatedAt").type(JsonFieldType.STRING).description("게시글 수정 날짜"),
                                fieldWithPath("data[].updatedBy").type(JsonFieldType.STRING).description("게시글 수정자")
                        )
                ));
    }

    @Test
    @WithMockUser
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
                member.getName(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                member.getName()
        );

        given(postService.updatePost(id, request)).willReturn(response);

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
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 수정 시간"),
                                fieldWithPath("updatedBy").type(JsonFieldType.STRING).description("게시글 수정자")
                        )
                ));
    }

    @Test
    @WithMockUser
    void 게시글_아이디로_삭제_호출_테스트() throws Exception {
        // Given
        Long memberId = 1L;
        String email = "test@gmail.com";

        MemberDetails memberDetails = new MemberDetails(Long.toString(memberId),
                "username",
                "password",
                List.of());

        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        memberDetails,
                        "",
                        memberDetails.getAuthorities()
                ));

        // When & Then
        mvc.perform(delete("/api/v1/posts/{id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", email))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("post-deleteById"));
    }

    @Test
    @WithMockUser
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
