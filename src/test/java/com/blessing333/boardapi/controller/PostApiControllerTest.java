package com.blessing333.boardapi.controller;

import com.blessing333.boardapi.TestDataProvider;
import com.blessing333.boardapi.controller.dto.PostCreateCommands;
import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import com.blessing333.boardapi.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestDataProvider dataProvider;
    @Autowired
    private PostRepository postRepository;

    private User testUser;
    private Post testPost;

    @BeforeAll
    void init() {
        testUser = dataProvider.insertUserToDb("tester", 26);
        testPost = dataProvider.insertPostToDb("title", "content", testUser);
    }

    @DisplayName("id로 단일 게시글 조회 요청시 게시글 정보 반환")
    @Test
    void getPostInformation() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/posts/" + testPost.getId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        PostInformation result = mapper.readValue(jsonString, PostInformation.class);

        assertThat(result.getId()).isEqualTo(testPost.getId());
        assertThat(result.getTitle()).isEqualTo(testPost.getTitle());
        assertThat(result.getContent()).isEqualTo(testPost.getContent());
        assertThat(result.getWriter()).isEqualTo(testPost.getWriter());
    }

    @DisplayName("존재하지 않는 게시글 조회 요청 시 PostNotFoundException 발생시키고 응답으로 404 Not found code 반환")
    @Test
    void getPostInformationWithInvalidId() throws Exception {
        String invalidId = "-1";
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/posts/" + invalidId))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("게시글 생성 요청시 게시글을 생성하고 생성된 게시글 정보를 반환한다")
    @Test
    void registerPostTest() throws Exception {
        String title = "title title";
        String content = "content content";
        PostCreateCommands commands = new PostCreateCommands(title, content, testUser.getId());
        String json = mapper.writeValueAsString(commands);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        PostInformation result = mapper.readValue(jsonString, PostInformation.class);
        Optional<Post> found = postRepository.findById(result.getId());
        Assertions.assertDoesNotThrow(() -> found.orElseThrow());
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getWriter()).isEqualTo(testPost.getWriter());
    }

    @DisplayName("잘못된 필드로 게시글 생성을 요청하면 예외 발생")
    @Test
    void registerPostWithInvalidValue() throws Exception {
        String title = "t"; // 제목 길이 2 미만
        String content = ""; // 내용 없음
        PostCreateCommands commands = new PostCreateCommands(title, content, testUser.getId());
        String json = mapper.writeValueAsString(commands);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertNotNull(mvcResult.getResolvedException().getMessage());
    }

}