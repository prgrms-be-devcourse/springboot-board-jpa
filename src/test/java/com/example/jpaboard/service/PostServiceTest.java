package com.example.jpaboard.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.example.jpaboard.post.application.PostService;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.dto.PostDto;
import com.example.jpaboard.user.domain.User;
import com.example.jpaboard.exception.PostNotFoundException;
import com.example.jpaboard.post.infra.PostRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    PostService postService;


    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository);
    }

    @Test
    void getPostDetails_returnsPostInfo() {
        Post post = new Post("test title", "test content");
        post.setAuthor(new User("tester", ""));

        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        PostDto postDto = postService.getPost(1L);
        assertThat(postDto.getTitle()).isEqualTo(post.getTitle());
        assertThat(postDto.getContent()).isEqualTo(post.getContent());
    }

    @Test
    void getPostDetails_willThrowException_whenPostNotFound() {
        given(postRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            postService.getPost(1L);
        }).isInstanceOf(PostNotFoundException.class);
    }
}