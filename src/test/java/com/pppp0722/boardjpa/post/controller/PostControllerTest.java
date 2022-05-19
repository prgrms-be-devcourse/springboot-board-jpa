package com.pppp0722.boardjpa.post.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pppp0722.boardjpa.domain.post.PostRepository;
import com.pppp0722.boardjpa.post.dto.PostDto;
import com.pppp0722.boardjpa.post.dto.UserDto;
import com.pppp0722.boardjpa.post.service.PostService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        LocalDateTime now = LocalDateTime.now();

        PostDto postDto = PostDto.builder()
            .createdAt(now)
            .createdBy("Ilhwan Lee")
            .title("I'm hungry")
            .content("I want pizza.")
            .userDto(
                UserDto.builder()
                    .createdAt(now)
                    .createdBy("Ilhwan Lee")
                    .name("Ilhwan Lee")
                    .age(27)
                    .hobby("Game")
                    .build()
            )
            .build();

        postService.save(postDto);
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    public void 게시글을_조회하면_객체를_반환한다() throws Exception {
        mockMvc.perform(get("/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }
}