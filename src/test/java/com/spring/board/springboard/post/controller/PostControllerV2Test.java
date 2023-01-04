package com.spring.board.springboard.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.springboard.post.domain.dto.PostCreateRequestDto;
import com.spring.board.springboard.post.domain.dto.PostDetailResponseDto;
import com.spring.board.springboard.post.service.PostService;
import com.spring.board.springboard.user.controller.authenticate.Session;
import com.spring.board.springboard.user.controller.authenticate.SessionManager;
import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.domain.dto.MemberDetailResponseDto;
import com.spring.board.springboard.user.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static com.spring.board.springboard.user.controller.authenticate.CookieUtils.createUserInfoCookie;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
class PostControllerV2Test {

    private static final String email = "user@naver.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionManager sessionManager;

    private static Member member;
    private static Session session;
    private static Cookie cookie;

    @BeforeEach
    void setUp() {
        member = new Member(email, "password1234", "아무개", 49, Hobby.SLEEP);
        memberRepository.save(member);

        session = sessionManager.getSessionOrCreateIfNotExist(
                new MemberDetailResponseDto(
                        memberRepository.findByEmail(email)
                                .get()));

        cookie = createUserInfoCookie(session.sessionId());
    }

    @DisplayName("모든 게시물을 불러올 수 있다.")
    @Test
    void getAllPosts() throws Exception {
        postService.createPost(
                new PostCreateRequestDto(
                        "새로운 글의 제목,",
                        "새로운 글의 내용"
                ), member.getEmail()
        );
        postService.createPost(
                new PostCreateRequestDto(
                        "새로운 글의 제목2,",
                        "새로운 글의 내용2"
                ), member.getEmail()
        );

        mockMvc.perform(get("/v2/posts")
                        .param("page",
                                String.valueOf(0))
                        .param("size",
                                String.valueOf(2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all-post",
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("post array"),
                                fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("[].memberName").type(JsonFieldType.STRING).description("writer name")
                        ))
                );
    }

    @DisplayName("인증된 사용자는 게시물을 새로 생성할 수 있다.")
    @Test
    void createPost() throws Exception {
        // given
        PostCreateRequestDto postCreateRequestDTO = new PostCreateRequestDto(
                "저장하기",
                "게시판에 새 글 저장하기");

        // when and then
        mockMvc.perform(post("/v2/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(cookie)
                        .content(objectMapper.writeValueAsString(postCreateRequestDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ))
                );
    }

    @DisplayName("인증되지 않은 사용자는 게시물을 새로 생성할 수 없다.")
    @Test
    void createPostFail() throws Exception {
        // given
        PostCreateRequestDto postCreateRequestDTO = new PostCreateRequestDto(
                "인증되지 않은 사용자",
                "인증되지 않은 사용자는 이 글을 등록할 수 없습니다.");

        // when and then
        mockMvc.perform(post("/v2/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequestDTO)))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("하나의 게시물을 조회할 수 있다.")
    @Test
    void getPost() throws Exception {
        // given
        PostDetailResponseDto newPost = postService.createPost(
                new PostCreateRequestDto(
                        "하나의 게시물 조회",
                        "하나의 게시물을 조회할 수 있다."
                ), member.getEmail()
        );

        mockMvc.perform(get("/v2/posts/{postId}", newPost.postId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-one-post",
                        pathParameters(
                                parameterWithName("postId").description("post id")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("created at"),
                                fieldWithPath("member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("member.email").type(JsonFieldType.STRING).description("member email"),
                                fieldWithPath("member.name").type(JsonFieldType.STRING).description("member name")
                        ))
                );
    }

    @DisplayName("작성한 사용자라면 게시물의 제목과 내용을 수정할 수 있다.")
    @Test
    void updatePost() throws Exception {
        PostDetailResponseDto newPost = postService.createPost(
                new PostCreateRequestDto(
                        "hi hello",
                        "hello world"
                ), member.getEmail()
        );

        PostCreateRequestDto updatePostDTO = new PostCreateRequestDto(
                "수정하기",
                "게시판 새 글 저장하기 -> 수정된 내용임"
        );

        mockMvc.perform(post("/v2/posts/{postId}", newPost.postId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(cookie)
                        .content(objectMapper.writeValueAsString(updatePostDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("created at"),
                                fieldWithPath("member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("member.email").type(JsonFieldType.STRING).description("member email"),
                                fieldWithPath("member.name").type(JsonFieldType.STRING).description("member name")
                        ))
                );
    }

    @DisplayName("작성하지 않은 사용자이거나, 인증되지 않은 사용자는 게시물의 제목과 내용을 수정할 수 있다.")
    @Test
    void updatePostFail() throws Exception {
        PostDetailResponseDto newPost = postService.createPost(
                new PostCreateRequestDto(
                        "hi hello",
                        "hello world"
                ), member.getEmail()
        );

        Cookie notWriterCookie = new Cookie("user", "not-user@naver.com");

        PostCreateRequestDto updatePostDTO = new PostCreateRequestDto(
                "수정하기",
                "게시판 새 글 저장하기 -> 수정된 내용임"
        );

        mockMvc.perform(post("/v2/posts/{postId}", newPost.postId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostDTO)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/v2/posts/{postId}", newPost.postId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(notWriterCookie)
                        .content(objectMapper.writeValueAsString(updatePostDTO)))
                .andExpect(status().isUnauthorized());
    }
}