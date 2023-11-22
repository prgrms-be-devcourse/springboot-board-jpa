package kdt.jpa.board.post.controller;

import kdt.jpa.board.common.TestContainerSupport;
import kdt.jpa.board.post.domain.Post;
import kdt.jpa.board.post.fixture.PostFixture;
import kdt.jpa.board.post.repository.PostRepository;
import kdt.jpa.board.post.service.PostService;
import kdt.jpa.board.post.service.dto.request.CreatePostRequest;
import kdt.jpa.board.post.service.dto.request.EditPostRequest;
import kdt.jpa.board.post.service.dto.response.PostResponse;
import kdt.jpa.board.user.domain.User;
import kdt.jpa.board.user.fixture.UserFixture;
import kdt.jpa.board.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("[Post Api 테스트]")
class PostControllerTest extends TestContainerSupport {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("[게시물 등록 API 를 호출한다]")
    void savePost() throws Exception {
        //given
        User user = userRepository.save(UserFixture.getUser());
        CreatePostRequest request = new CreatePostRequest("title", "content", user.getId());

        //when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andExpect(status().isOk());
        List<Post> all = postRepository.findAll();
        assertThat(all).hasSize(1);
    }

    @Test
    @DisplayName(("[단건 게시물 조회 API 를 호출한다]"))
    void getPost() throws Exception {
        //given
        User user = userRepository.save(UserFixture.getUser());
        Post post = new Post("title", "content", user);
        postRepository.save(post);

        //when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/posts/" + post.getId())
        );

        //then
        perform.andExpectAll(
                status().isOk(),
                jsonPath("$.title").value(post.getTitle()),
                jsonPath("$.content").value(post.getContent()),
                jsonPath("$.userName").value(user.getName())
        );
    }

    @Test
    @DisplayName("[전체 게시물 페이징 조회 API 를 호출한다]")
    void getPosts() throws Exception {
        //given
        User user = userRepository.save(UserFixture.getUser());
        Post post1 = new Post("title", "content", user);
        Post post2 = new Post("title", "content", user);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/posts")
        );

        //then
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[게시물 수정 API 를 호출한다]")
    void updatePost() throws Exception {
        //given
        User user = userRepository.save(UserFixture.getUser());
        Post post = new Post("title", "content", user);
        postRepository.save(post);
        EditPostRequest request = new EditPostRequest(post.getId(), "title1", "content1");

        //when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/posts" + post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andExpect(status().isOk());
    }
}
