package com.programmers.iyj.springbootboard.domain.post.service;

import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import com.programmers.iyj.springbootboard.domain.post.dto.PostDto;
import com.programmers.iyj.springbootboard.domain.post.repository.PostRepository;
import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import com.programmers.iyj.springbootboard.domain.user.repository.UserRepository;
import com.programmers.iyj.springbootboard.domain.user.service.UserService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private static Post post;
    private static PostDto postDto;

    @BeforeAll
    public static void setUp() {
        post = Post.builder()
                .id(1L)
                .title("title1")
                .content("content1")
                .build();

        postDto = PostDto.builder()
                .id(1L)
                .title("title1")
                .content("content1")
                .build();
    }

    @Test
    @DisplayName("Post can be selected by post_id")
    void selectUserById() throws NotFoundException {
        // Given
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // When
        PostDto postDto = postService.findOneById(1L);

        // Then
        assertThat(postDto.getTitle()).isEqualTo("title1");
        then(postRepository)
                .should()
                .findById(1L);
    }

    @Test
    @DisplayName("All posts can be selected")
    void selectAll() {
        // Given
        PageRequest page = PageRequest.of(0, 10);
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        Page<Post> postPage = new PageImpl<>(posts);
        given(postRepository.findAll(page)).willReturn(postPage);

        // When
        Page<PostDto> all = postService.findAll(page);

        // Then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("User should be created")
    void createPost() {
        // Given
        given(postRepository.save(any())).willReturn(post);

        // When
        PostDto savedPostDto = postService.save(postDto);

        // Then
        then(postRepository)
                .should()
                .save(post);

        assertThat(savedPostDto).isEqualTo(postDto);
    }
}