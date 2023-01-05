package com.prgrms.web.api.v2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.domain.post.PostRepository;
import com.prgrms.domain.post.PostService;
import com.prgrms.domain.user.UserService;
import com.prgrms.dto.PostDto.Request;
import com.prgrms.dto.PostDto.Response;
import com.prgrms.dto.PostDto.Update;
import com.prgrms.dto.UserDto;
import com.prgrms.dto.UserDto.UserCreateRequest;
import com.prgrms.web.auth.SessionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("sessionCookie 를 적용한 postController 테스트")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PostApiVer2Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    SessionManager sessionManager;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private UserDto.Response savedUser;
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final String SESSION_NAME = "userId";

    private String toJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @BeforeAll
    void setUp() {
        UserCreateRequest source = new UserCreateRequest(
            "회원1",
            "테스트 코드 짜기",
            10,
            "user1@gmail.com",
            "1q1q1q1q!");

        savedUser = userService.insertUser(source);
        sessionManager.checkDuplicateLoginAndRegisterSession(
            savedUser.getUserId(),
            request,
            response);
    }

    @DisplayName("게시글을 생성할 수 있다")
    @Test
    void createPost() throws Exception {

        Request source = new Request("제목", "내용");

        mockMvc.perform(post("/api/v2/posts")
                .content(toJsonString(source))
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(response.getCookie(SESSION_NAME)))
            .andExpect(status().isCreated());
    }

    @DisplayName("로그인을 하지 않을 경우 게시글을 작성할 수 없다")
    @Test
    void createPostFail() throws Exception {
        Request source = new Request("제목", "내용");

        mockMvc.perform(post("/api/v2/posts")
                .content(toJsonString(source))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @DisplayName("본인의 게시글을 수정할 수 있다")
    @Test
    void editPost() throws Exception {
        Request createSource = new Request("제목", "내용");
        Update updateSource = new Update("수정한 제목", "수정한 내용");

        Response createdPost = postService.insertPost(savedUser.getUserId(), createSource);

        mockMvc.perform(patch("/api/v2/posts/" + createdPost.getPostId())
            .content(toJsonString(updateSource))
            .contentType(MediaType.APPLICATION_JSON)
            .cookie(response.getCookie(SESSION_NAME)))
            .andExpect(status().isOk());
    }

    @DisplayName("본인의 게시글이 아닌 게시글은 수정할 수 없다")
    @Test
    void editPostFail() throws Exception {

        UserCreateRequest userSource = new UserCreateRequest(
            "회원2",
            "노래듣기",
            10,
            "user2@gmail.com",
            "2q2q2q2q!");

        UserDto.Response savedUser2 = userService.insertUser(userSource);

        Request createSource = new Request("제목", "내용");
        Update updateSource = new Update("수정한 제목", "수정한 내용");

        Response createdPost = postService.insertPost(savedUser2.getUserId(), createSource);

        mockMvc.perform(patch("/api/v2/posts/" + createdPost.getPostId())
                .content(toJsonString(updateSource))
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(response.getCookie(SESSION_NAME)))
            .andExpect(status().isUnauthorized());
    }

    @DisplayName("본인의 게시글을 삭제할 수 있다")
    @Test
    void deletePost() throws Exception{

        Request createSource = new Request("제목", "내용");
        Response createdPost = postService.insertPost(savedUser.getUserId(), createSource);

        mockMvc.perform(delete("/api/v2/posts/" + createdPost.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(response.getCookie(SESSION_NAME)))
            .andExpect(status().isNoContent());
    }

    @DisplayName("본인의 게시글이 아니면 삭제할 수 없다")
    @Test
    void deletePostFail() throws Exception{
        UserCreateRequest userSource = new UserCreateRequest(
            "회원3",
            "영화보기",
            10,
            "user3@gmail.com",
            "3q3q3q3q!");

        UserDto.Response savedUser3 = userService.insertUser(userSource);
        Request createSource = new Request("제목", "내용");
        Response createdPost = postService.insertPost(savedUser3.getUserId(), createSource);

        mockMvc.perform(delete("/api/v2/posts/" + createdPost.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(response.getCookie(SESSION_NAME)))
            .andExpect(status().isUnauthorized());
    }

}
