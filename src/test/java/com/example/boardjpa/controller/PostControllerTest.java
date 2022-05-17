package com.example.boardjpa.controller;

import com.example.boardjpa.domain.User;
import com.example.boardjpa.dto.CreatePostRequestDto;
import com.example.boardjpa.dto.UpdatePostRequestDto;
import com.example.boardjpa.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostController postController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreatePost() throws Exception {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        CreatePostRequestDto createPostRequestDto
                = new CreatePostRequestDto("제목", "내용", user.getId());

        //When //Then
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequestDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testFindPost() throws Exception {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        Long postId = postController
                .createPost(new CreatePostRequestDto("제목", "내용", user.getId()))
                .getBody();

        //When //Then
        mockMvc.perform(get("/api/v1/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testFindPosts() throws Exception {
        //Given
        User user = userRepository
                .save(new User("박상혁", 25, "음주"));
        Long postId = postController
                .createPost(new CreatePostRequestDto("제목", "내용", user.getId()))
                .getBody();

        //When //Then
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //TODO 추후 에러 원인 파악 및 수정 필요
//    @Test
//    void testUpdatePost() throws Exception {
//        //Given
//        User user = userRepository
//                .save(new User("박상혁", 25, "음주"));
//        Long postId = postController
//                .createPost(new CreatePostRequestDto("제목", "내용", user.getId()))
//                .getBody();
//
//        //When //Then
//        UpdatePostRequestDto updatePostRequestDto = new UpdatePostRequestDto("바뀐 내용");
//        mockMvc.perform(post("/api/v1/posts/{postId}", postId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatePostRequestDto)))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
}