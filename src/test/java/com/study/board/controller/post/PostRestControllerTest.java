package com.study.board.controller.post;

import com.study.board.controller.PostRestController;
import com.study.board.controller.support.RestDocsTestSupport;
import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.repository.PostRepository;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.study.board.fixture.Fixture.createUser;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PostRestControllerTest extends RestDocsTestSupport {

    @Autowired
    PostRestController controller;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    Post post1;
    Post post2;

    @BeforeEach
    void setUp() {
        User writer = createUser();
        userRepository.save(writer);

        post1 = postRepository.save(new Post("제목1", "내용1", writer));
        post2 = postRepository.save(new Post("제목2", "내용2", writer));
    }

    @Test
    void 게시글_페이징_조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpectAll(
                        jsonPath("$[0].id").exists(),
                        jsonPath("$[0].title", "제목1").exists(),
                        jsonPath("$[0].content", "내용1").exists(),
                        jsonPath("$[0].writer", "득윤").exists(),
                        jsonPath("$[0].writtenDateTime").exists()
                ).andExpectAll(
                        jsonPath("$[1].id").exists(),
                        jsonPath("$[1].title", "제목2").exists(),
                        jsonPath("$[1].content", "내용2").exists(),
                        jsonPath("$[1].writer", "득윤").exists(),
                        jsonPath("$[1].writtenDateTime").exists()
                );
    }

    @Test
    void 게시글_단건_조회() throws Exception {
        Long targetPostId = post1.getId();

        mockMvc.perform(get("/posts/" + targetPostId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id", targetPostId).exists(),
                        jsonPath("$.title", "제목1").exists(),
                        jsonPath("$.content", "내용1").exists(),
                        jsonPath("$.writer", "득윤").exists(),
                        jsonPath("$.writtenDateTime").exists()
                );
    }
}
