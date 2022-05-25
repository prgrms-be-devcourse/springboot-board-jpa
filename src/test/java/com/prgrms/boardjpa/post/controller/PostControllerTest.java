package com.prgrms.boardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardjpa.domain.User;
import com.prgrms.boardjpa.post.dao.PostRepository;
import com.prgrms.boardjpa.post.dto.PostReqDto;
import com.prgrms.boardjpa.post.dto.PostUpdateDto;
import com.prgrms.boardjpa.post.service.PostService;
import com.prgrms.boardjpa.user.dao.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Long postId;

    private User testUser;

    @BeforeEach
    void setUp() {
        User insertUser = new User();
        insertUser.setAge(24);
        insertUser.setHobby("soccer");
        insertUser.setName("test user");
        // Augo Generate Identity로 걸어두면 id set해도 그 값으로 들어가지 않음. identity 방식 다시 공부하기

        testUser = userRepository.save(insertUser);

        PostReqDto postDto = PostReqDto.builder()
                .title("새 게시글")
                .content("새 내용")
                .userId(testUser.getId())
                .build();

        postId = postService.save(postDto);

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 다건 조회 api")
    void getAll() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 단건 조회 api")
    void getOne() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{postId}",postId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 조회 api")
    void save() throws Exception {
        PostReqDto postDto = PostReqDto.builder()
                .title("새 게시글 작성 테스트")
                .content("새 내용 작성 테스트")
                .userId(testUser.getId())
                .build();

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 수정 api")
    void update() throws Exception {
        PostUpdateDto postDto = PostUpdateDto.builder()
                .title("수정된 제목")
                .content("수정된 본문")
                .build();

        mockMvc.perform(put("/api/v1/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}