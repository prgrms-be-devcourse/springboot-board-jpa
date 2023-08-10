package com.kdt.board.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.domain.post.dto.PostCreateRequestDto;
import com.kdt.board.domain.post.entity.Post;
import com.kdt.board.domain.post.service.PostService;
import com.kdt.board.domain.user.entity.User;
import com.kdt.board.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .name("kanginlee")
                .age(23)
                .hobby("soccer")
                .posts(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("User와 Post를 받아 Post를 성공적으로 저장할 수 있다")
    void givenUserAndPost_whenSavePost_thenReturnsOk() throws Exception {

        PostCreateRequestDto request = PostCreateRequestDto.builder()
                .title("testTitle")
                .content("testContent")
                .userId(1L)
                .build();

        Post post = PostCreateRequestDto.from(request, user);

        given(userService.findById(any())).willReturn(user);
        given(postService.save(any(Post.class))).willReturn(post);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID")
                        )));
    }

    @Test
    @DisplayName("Pageable 을 이용하여 모든 게시글을 조회할 수 있다")
    void whenFindAll_thenReturnsPosts() throws Exception {
        List<Post> posts = new ArrayList<>();
        Post post1 = Post.builder()
                .id(1L)
                .title("title1")
                .content("content1")
                .user(user)
                .build();

        Post post2 = Post.builder()
                .id(2L)
                .title("title2")
                .content("content2")
                .user(user)
                .build();
        posts.add(post1);
        posts.add(post2);

        Page<Post> postPage = new PageImpl<>(posts);

        given(postService.findAll(any())).willReturn(postPage);

        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all-posts",
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.ARRAY).description("게시글 목록"),
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.NULL).description("작성 날짜"),
                                fieldWithPath("content[].createdBy.id").type(JsonFieldType.NUMBER).description("작성자 ID"),
                                fieldWithPath("content[].createdBy.name").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("content[].createdBy.age").type(JsonFieldType.NUMBER).description("작성자 나이"),
                                fieldWithPath("content[].createdBy.hobby").type(JsonFieldType.STRING).description("작성자 취미"),
                                fieldWithPath("content[].createdBy.posts").type(JsonFieldType.ARRAY).description("작성자가 작성한 게시글 목록"),
                                fieldWithPath("content[].createdBy.createdAt").type(JsonFieldType.NULL).description("작성자가 작성한 게시글 목록"),
                                fieldWithPath("content[].createdBy.createdBy").type(JsonFieldType.NULL).description("작성자가 작성한 게시글 목록"),
                                fieldWithPath("pageable").description("페이지 정보"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("전체 항목 수"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지 여부"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("전체 페이지 크기"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("현재 페이지 위치"),
                                fieldWithPath("sort").type(JsonFieldType.OBJECT).description("정렬 객체"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 객체 비어있는지 여부"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 X"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 O"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("항목의 갯수"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("비어있는지 여부")
                        )));

    }
}


