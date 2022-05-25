package com.study.board.controller.post;

import com.study.board.controller.PostRestController;
import com.study.board.controller.dto.PostResponse;
import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.repository.PostRepository;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.study.board.fixture.Fixture.createUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostRestControllerTest {

    @Autowired
    MockMvc mockMvc;

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

        Post post1 = new Post("제목1", "내용1", writer);
        Post post2 = new Post("제목2", "내용2", writer);

        postRepository.save(post1);
        postRepository.save(post2);
    }


    @Test
    void 게시글_페이징_조회() throws Exception {
         List<PostResponse> expectedPostResponses =
                 List.of(PostResponse.convert(post1), PostResponse.convert(post2));


        mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("제목1"))
                .andDo(print());
    }
}
