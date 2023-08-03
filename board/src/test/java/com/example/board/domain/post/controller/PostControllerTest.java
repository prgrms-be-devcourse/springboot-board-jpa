package com.example.board.domain.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.domain.post.service.PostService;
import com.example.board.domain.user.dto.UserCreateRequest;
import com.example.board.domain.user.dto.UserResponse;
import com.example.board.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  @Autowired
  private PostRepository postRepository;

  private PostResponse postResponse;
  private PostCreateRequest postCreateRequest;

  @BeforeEach
  public void setUp() {
    UserCreateRequest userCreateRequest = new UserCreateRequest("배준일", 20, "배드민턴");
    UserResponse userResponse = userService.createUser(userCreateRequest);

    postCreateRequest = new PostCreateRequest(userResponse.id(), "제목", "내용");
    postResponse = postService.createPost(postCreateRequest);
  }

  @AfterEach
  void tearDown() {
    postRepository.deleteAll();
  }

  @Test
  @DisplayName("성공 - 게시글 등록")
  void createPost() throws Exception {

    //given

    //when

    //then
    mockMvc.perform(post("/api/posts")
            .content(objectMapper.writeValueAsString(postCreateRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(document("post-create",
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                )
            )
        );
  }

  @Test
  @DisplayName("성공 - 게시글 단건 조회")
  void getPost() throws Exception {

    // given
    Long savedPostId = postResponse.id();

    // when

    // then
    mockMvc.perform(get("/api/posts/{postId}", savedPostId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("post-get",
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("createdBy")
            )
        ));
  }

  @Test
  @DisplayName("성공 - 게시글 페이징 조회")
  void getPosts() throws Exception {
    //given
    List<PostResponse> postsList = Arrays.asList(
        postResponse,
        new PostResponse(postResponse.id() + 1L, "제목2", "내용2", "2023-08-03 18:00", "배준일")
    );

    Page<PostResponse> postsPage = new PageImpl<>(postsList, PageRequest.of(0, 10),
        postsList.size());
    Pageable pageable = PageRequest.of(0, 10);

    //when

    //then

    mockMvc.perform(get("/api/posts")
            .param("page", String.valueOf(pageable.getPageNumber()))
            .param("size", String.valueOf(pageable.getPageSize())))
        .andExpect(status().isOk())
        .andDo(document("post-get-page",
            responseFields(
                fieldWithPath("content.[].id").type(JsonFieldType.NUMBER).description("postId"),
                fieldWithPath("content.[].title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content.[].content").type(JsonFieldType.STRING)
                    .description("content"),
                fieldWithPath("content.[].createdAt").type(JsonFieldType.STRING)
                    .description("createdAt"),
                fieldWithPath("content.[].createdBy").type(JsonFieldType.STRING)
                    .description("createdBy"),
                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                    .description("sort.empty"),
                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                    .description("sort.sorted"),
                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("sort.unsorted"),
                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("offset"),
                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER)
                    .description("pageNumber"),
                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER)
                    .description("pageSize"),
                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN)
                    .description("unpaged"),
                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                fieldWithPath("totalElements").type(JsonFieldType.NUMBER)
                    .description("totalElements"),
                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER)
                    .description("numberOfElements"),
                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty"),
                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("sort.unsorted"),
                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted")
            )));
  }

  @Test
  @DisplayName("성공 - 게시글 수정")
  void updatePost() throws Exception {

    // given
    Long savedPostId = postResponse.id();
    PostUpdateRequest postUpdateRequest = new PostUpdateRequest("제목1", "내용1");

    // when

    // then
    mockMvc.perform(put("/api/posts/{postId}", savedPostId)
            .content(objectMapper.writeValueAsString(postUpdateRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("post-update",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
            )
        ));
  }
}