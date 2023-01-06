package com.example.board.domain.post.api;

import com.example.board.config.RestDocsConfiguration;
import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.service.PostService;
import com.example.board.domain.user.dto.UserDto;
import com.example.board.domain.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestDocumentationResultHandler restDocs;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(restDocs)
                .build();
    }

    private final User user = User.builder()
            .name("박현서")
            .age(3)
            .build();

    private final UserDto.SingleUserSimpleResponse singleUserSimpleResponse
            = UserDto.SingleUserSimpleResponse.toResponse(user);

    private final Post post = Post.builder()
            .title("테스트게시글제목")
            .content("테스트게시글내용")
            .user(user)
            .build();

    private final PostDto.CreatePostRequest createPostRequest
            = new PostDto.CreatePostRequest(1L, post.getTitle(), post.getContent());

    private final PostDto.SinglePostResponse singlePostResponse
            = new PostDto.SinglePostResponse(1L, post.getTitle(), post.getContent(), singleUserSimpleResponse);

    private final PostDto.UpdatePostRequest updatePostRequest
            = new PostDto.UpdatePostRequest("업데이트될 게시글 제목", "업데이트될 게시글 내용");

    @DisplayName("게시글 저장을 성공한다.")
    @Test
    void save_post_success() throws Exception {
        when(postService.post(any()))
                .thenReturn(singlePostResponse);

        String requestBody = objectMapper.writeValueAsString(createPostRequest);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("userId").description("회원 번호"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.postId").description("게시글 번호"),
                                fieldWithPath("stock.title").description("게시글 제목"),
                                fieldWithPath("stock.content").description("게시글 내용"),
                                fieldWithPath("stock.user.name").description("회원 이름")
                        )
                ));
    }

    @DisplayName("게시글 리스트 페이징 조회에 성공합니다.")
    @Test
    void get_paged_posts_success() throws Exception {
        PageRequest pageable = PageRequest.of(0, 1);
        Page<PostDto.SinglePostResponse> pagedPostsResponse
                = new PageImpl<>(List.of(singlePostResponse), pageable, 1);

        when(postService.pagingPost(any()))
                .thenReturn(pagedPostsResponse);

        mockMvc.perform(get("/posts")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        queryParameters(
                                parameterWithName("page").description("게시글 페이지 번호"),
                                parameterWithName("size").description("게시글 페이징 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.content.[].postId").description("게시글 번호"),
                                fieldWithPath("stock.content.[].title").description("게시글 제목"),
                                fieldWithPath("stock.content.[].content").description("게시글 내용"),
                                fieldWithPath("stock.content.[].user.name").description("회원 이름"),
                                fieldWithPath("stock.pageInfo.first").description("게시글 첫 페이지 O"),
                                fieldWithPath("stock.pageInfo.last").description("게시글 마지막 페이지 O"),
                                fieldWithPath("stock.pageInfo.hasNext").description("게시글 다음 페이지 O"),
                                fieldWithPath("stock.pageInfo.totalPages").description("게시글 총 페이지"),
                                fieldWithPath("stock.pageInfo.totalElements").description("게시글 총 개수"),
                                fieldWithPath("stock.pageInfo.page").description("게시글 페이지"),
                                fieldWithPath("stock.pageInfo.size").description("게시글 사이즈")
                        )
                ));
    }

    @DisplayName("게시글 단건 조회에 성공합니다.")
    @Test
    void get_post_success() throws Exception {
        when(postService.getPost(anyLong()))
                .thenReturn(singlePostResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", singlePostResponse.postId()))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("id").description("게시글 번호")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.postId").description("게시글 번호"),
                                fieldWithPath("stock.title").description("게시글 제목"),
                                fieldWithPath("stock.content").description("게시글 내용"),
                                fieldWithPath("stock.user.name").description("회원 이름")
                        )
                ));
    }

    @DisplayName("게시글 수정을 성공합니다.")
    @Test
    void patch_post_success() throws Exception {
        when(postService.updatePost(anyLong(), any()))
                .thenReturn(singlePostResponse);

        String requestBody = objectMapper.writeValueAsString(updatePostRequest);

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/posts/{id}", singlePostResponse.postId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("id").description("게시글 번호")
                        ),
                        requestFields(
                                fieldWithPath("title").description("새로운 게시글 제목"),
                                fieldWithPath("content").description("새로운 게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.postId").description("게시글 번호"),
                                fieldWithPath("stock.title").description("게시글 제목"),
                                fieldWithPath("stock.content").description("게시글 내용"),
                                fieldWithPath("stock.user.name").description("회원 이름")
                        )
                ));
    }
}