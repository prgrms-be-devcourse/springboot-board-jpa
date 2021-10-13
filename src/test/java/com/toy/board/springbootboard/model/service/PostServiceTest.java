package com.toy.board.springbootboard.model.service;

import com.toy.board.springbootboard.model.domain.Post;
import com.toy.board.springbootboard.model.dto.PostDto;
import com.toy.board.springbootboard.model.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class PostServiceTest {
    private PostService postService;
    private PostRepository postRepository;

    @Autowired
    PostServiceTest(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }
    
//    @BeforeEach
//    void setUP(){
//        // Given
//        PostDto postDto = PostDto.builder()
//                .id()
//    }
}