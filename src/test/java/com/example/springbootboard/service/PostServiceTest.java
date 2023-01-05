package com.example.springbootboard.service;

import com.example.springbootboard.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Test
    void postSaveTest() throws Exception {
        PostDto postDto = PostDto.builder().title("test_title").content("test_content").build();
        postService.createPost(postDto);

        PostDto retrieved = postService.findPostById(0L);

        assertThat(retrieved.getTitle()).isEqualTo("test_title");
        assertThat(retrieved.getContent()).isEqualTo("test_content");
    }
}
