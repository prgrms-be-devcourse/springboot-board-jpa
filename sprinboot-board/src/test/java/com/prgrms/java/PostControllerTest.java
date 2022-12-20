package com.prgrms.java;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.domain.Post;
import com.prgrms.java.domain.User;
import com.prgrms.java.dto.CreatePostRequest;
import com.prgrms.java.dto.ModifyPostRequest;
import com.prgrms.java.repository.PostRepository;
import com.prgrms.java.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getPosts() throws Exception {
        User user = userRepository.save(new User("이택승", 25, HobbyType.MOVIE));
        Post post1 = new Post("데브코스 짱짱", "데브코스 짱짱입니다.", user);
        Post post2 = new Post("데브코스 짱짱2", "데브코스 짱짱2입니다.", user);
        List<Post> posts = postRepository.saveAll(List.of(post1, post2));

        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getPostDetails() throws Exception {
        User user = userRepository.save(new User("이택승", 25, HobbyType.MOVIE));
        Post post = postRepository.save(new Post("데브코스 짱짱2", "데브코스 짱짱2입니다.", user));

        mockMvc.perform(get("/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void createPost() throws Exception {
        User user = userRepository.save(new User("이택승", 25, HobbyType.MOVIE));

        CreatePostRequest createPostRequest = new CreatePostRequest(
                "데브코스 짱짱",
                "데브코스 짱짱입니다.",
                user.getId()
        );

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void modifyPost() throws Exception {
        User user = userRepository.save(new User("이택승", 25, HobbyType.MOVIE));
        Post post = postRepository.save(new Post("데브코스 짱짱", "데브코스 짱짱입니다.", user));

        ModifyPostRequest request = new ModifyPostRequest("데브코스 좋아", "데브코스 좋아용!");

        mockMvc.perform(post("/posts/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}