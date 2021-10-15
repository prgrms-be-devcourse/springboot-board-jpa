package kdt.prgrms.devrun.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import kdt.prgrms.devrun.post.dto.AddPostRequestDto;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.EditPostRequestDto;
import kdt.prgrms.devrun.post.service.PostService;
import kdt.prgrms.devrun.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PostApiController 테스트")
class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private final Long VALID_POST_ID = 1L;
    private final String BASE_URI = "/api/v1/posts";

    private User user;

    @BeforeAll
    void setUp() {

        user = User.builder()
            .loginId("kjt3520")
            .loginPw("1234")
            .age(27)
            .name("김지훈")
            .email("devrunner21@gmail.com")
            .posts(new ArrayList<Post>())
            .build();

        userRepository.save(user);

        IntStream.range(0, 30).forEach(i -> postService.createPost(AddPostRequestDto.builder().title("제목 " + i).content("내용").createdBy(user.getLoginId()).build()));

    }

    @Test
    @DisplayName("게시글 페이징 목록 조회 테스트")
    void test_posts() throws Exception {

        mockMvc.perform(get(BASE_URI)
            .param("page", String.valueOf(0))
            .param("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("게시글 단건(상세) 조회 테스트")
    void test_post() throws Exception {

        mockMvc.perform(get(BASE_URI + "/{id}", VALID_POST_ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void test_create() throws Exception {

        final AddPostRequestDto postForm = AddPostRequestDto.builder()
            .title("New Post Title")
            .content("New Post Content")
            .createdBy(user.getLoginId())
            .build();

        mockMvc.perform(post(BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postForm)))
            .andExpect(status().isCreated())
            .andDo(print());

    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void test_update() throws Exception {

        final EditPostRequestDto postForm = EditPostRequestDto.builder()
            .title("New Post Title")
            .content("New Post Content")
            .createdBy(user.getLoginId())
            .build();

        mockMvc.perform(patch(BASE_URI + "/{id}", VALID_POST_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postForm)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void test_delete() throws Exception {

        final Long savedPostId = postService.createPost(AddPostRequestDto.builder().title("삭제할 게시글 제목").content("삭제할 게시글 내용").createdBy(user.getLoginId()).build());

        mockMvc.perform(delete(BASE_URI + "/{id}", savedPostId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print());

    }

}
