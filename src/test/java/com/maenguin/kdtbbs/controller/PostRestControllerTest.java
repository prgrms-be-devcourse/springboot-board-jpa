package com.maenguin.kdtbbs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.maenguin.kdtbbs.domain.Post;
import com.maenguin.kdtbbs.domain.User;
import com.maenguin.kdtbbs.dto.PostAddDto;
import com.maenguin.kdtbbs.dto.PostAddResDto;
import com.maenguin.kdtbbs.exception.ErrorCode;
import com.maenguin.kdtbbs.repository.UserRepository;
import com.maenguin.kdtbbs.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    private Long testUserId;

    private Long testPostId;

    @BeforeEach
    void setUp() {
        User user = new User("maeng", "piano");
        Post post = new Post("hello", "world");
        user.addPost(post);
        User savedUser = userRepository.save(user);
        testUserId = savedUser.getUserId();
        testPostId = savedUser.getPosts().get(0).getPostId();
    }

    @Test
    @DisplayName("게시글 목록 페이징 조회 성공 테스트 (page=0, size=3)")
    void searchAllPostsSuccessTest() throws Exception {

        //given
        Long no2 = postService.savePost(new PostAddDto(testUserId, "no2", "2")).getPostId();
        Long no3 = postService.savePost(new PostAddDto(testUserId, "no3", "2")).getPostId();
        Long no4 = postService.savePost(new PostAddDto(testUserId, "no4", "2")).getPostId();
        //when
        ResultActions result = mockMvc.perform(
                get("/api/v1/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "3")
        );
        //then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("searchAllPosts"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data.postList").isArray())
                .andExpect(jsonPath("$.data.postList.length()", is(3)))
                .andExpect(jsonPath("$.data..id", containsInAnyOrder(no2.intValue(), no3.intValue(), no4.intValue())))
                .andExpect(jsonPath("$.data.pagination.totalPages", is(2)))
                .andExpect(jsonPath("$.data.pagination.totalElements", is(4)))
                .andExpect(jsonPath("$.data.pagination.currentPage", is(0)))
                .andExpect(jsonPath("$.data.pagination.offset", is(0)))
                .andExpect(jsonPath("$.data.pagination.size", is(3)));
    }

    @Test
    @DisplayName("게시글 목록 페이징 조회 성공 테스트 (page=1, size=3)")
    void searchAllPostsSuccessTest2() throws Exception {

        //given
        postService.savePost(new PostAddDto(testUserId, "no2", "2"));
        postService.savePost(new PostAddDto(testUserId, "no3", "2"));
        postService.savePost(new PostAddDto(testUserId, "no4", "2"));
        //when
        ResultActions result = mockMvc.perform(
                get("/api/v1/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "3")
        );
        //then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("searchAllPosts"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data.postList").isArray())
                .andExpect(jsonPath("$.data.postList.length()", is(1)))
                .andExpect(jsonPath("$.data.pagination.totalPages", is(2)))
                .andExpect(jsonPath("$.data.pagination.totalElements", is(4)))
                .andExpect(jsonPath("$.data.pagination.currentPage", is(1)))
                .andExpect(jsonPath("$.data.pagination.offset", is(3)))
                .andExpect(jsonPath("$.data.pagination.size", is(3)));
    }

    @Test
    @DisplayName("게시글 조회 성공 테스트")
    void searchPostByIdSuccessTest() throws Exception {

        //when
        ResultActions result = mockMvc.perform(
                get("/api/v1/posts/{id}", testPostId)
                        .accept(MediaType.APPLICATION_JSON)
        );
        //then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("searchPostById"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data.id", is(testPostId.intValue())))
                .andExpect(jsonPath("$.data.title", is("hello")))
                .andExpect(jsonPath("$.data.content", is("world")));
    }

    @Test
    @DisplayName("게시글 조회 실패 테스트 (존재하지 않는 게시글)")
    void searchPostByIdFailureTest() throws Exception {

        //when
        ResultActions result = mockMvc.perform(
                get("/api/v1/posts/{id}", -1L)
                        .accept(MediaType.APPLICATION_JSON)
        );
        //then
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("searchPostById"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error.errorCode", is(ErrorCode.POST_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.error.message", containsString(ErrorCode.POST_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("게시글 등록 성공 테스트")
    void registerPostSuccessTest() throws Exception {

        //given
        PostAddDto postAddDto = new PostAddDto(testUserId, "hi", "ru");
        //when
        ResultActions registerResult = mockMvc.perform(
                post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(postAddDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        registerResult.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("registerPost"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data.postId", is(notNullValue())));
        int savedId = JsonPath.read(registerResult.andReturn().getResponse().getContentAsString(), "$.data.postId");
        //then
        ResultActions findResult = mockMvc.perform(
                get("/api/v1/posts/{id}", savedId)
                        .accept(MediaType.APPLICATION_JSON)
        );
        findResult.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("searchPostById"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data.id", is(savedId)))
                .andExpect(jsonPath("$.data.title", is("hi")))
                .andExpect(jsonPath("$.data.content", is("ru")));
    }

    @Test
    @DisplayName("게시글 등록 실패 테스트 (존재하지 않는 유저 id)")
    void registerPostFailureTest() throws Exception {

        //given
        PostAddDto postAddDto = new PostAddDto(-1L, "hi", "ru");
        //when
        ResultActions result = mockMvc.perform(
                post("/api/v1/posts")
                        .content(objectMapper.writeValueAsString(postAddDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        //then
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("registerPost"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.data", is(nullValue())))
                .andExpect(jsonPath("$.error.errorCode", is(ErrorCode.USER_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.error.message", containsString(ErrorCode.USER_NOT_FOUND.getMessage())));
    }

    @Test
    @DisplayName("게시글 수정 성공 테스트")
    void editPostSuccessTest() throws Exception {

        //given
        PostAddResDto postAddResDto = postService.savePost(new PostAddDto(testUserId, "hi", "ru"));
        Long savedId = postAddResDto.getPostId();
        //when
        PostAddDto postAddDto2 = new PostAddDto(1L, "hi2", "ru2");
        ResultActions editResult = mockMvc.perform(
                post("/api/v1/posts/{id}", savedId)
                        .content(objectMapper.writeValueAsString(postAddDto2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        editResult.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("editPost"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data.postId", is(notNullValue())));
        int editId = JsonPath.read(editResult.andReturn().getResponse().getContentAsString(), "$.data.postId");
        //then
        ResultActions findResult = mockMvc.perform(
                get("/api/v1/posts/{id}", editId)
                        .accept(MediaType.APPLICATION_JSON)
        );
        findResult.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PostRestController.class))
                .andExpect(handler().methodName("searchPostById"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.error", is(nullValue())))
                .andExpect(jsonPath("$.data.id", is(editId)))
                .andExpect(jsonPath("$.data.title", is("hi2")))
                .andExpect(jsonPath("$.data.content", is("ru2")));
    }
}
