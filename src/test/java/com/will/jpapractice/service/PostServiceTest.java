package com.will.jpapractice.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.will.jpapractice.domain.post.application.PostService;
import com.will.jpapractice.domain.post.domain.Post;
import com.will.jpapractice.domain.post.dto.PostRequest;
import com.will.jpapractice.domain.post.dto.PostResponse;
import com.will.jpapractice.domain.post.repository.PostRepository;
import com.will.jpapractice.domain.user.domain.User;
import com.will.jpapractice.domain.user.repository.UserRepository;
import com.will.jpapractice.global.converter.Converter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Converter converter;

    @Mock
    User user;

    @Mock
    Post post;

    @Test
    @DisplayName("새로운 게시글을 생성한다.")
    void test_create_post() throws Exception {
        PostRequest request = new PostRequest("this is title", "this is content");
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(converter.toPost(request, user)).willReturn(post);
        given(post.getId()).willReturn(1L);
        given(postRepository.save(post)).willReturn(post);

        Long id = postService.save(1L, request);

        then(postRepository).should().save(post);
        assertThat(id).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글을 수정한다.")
    void test_update_post() throws Exception {
        PostRequest request = new PostRequest("this is updated title", "this is updated content");
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(post.getId()).willReturn(1L);

        Long updatedPostId = postService.update(1L, request);

        then(postRepository).should().findById(1L);
        assertThat(updatedPostId).isEqualTo(1L);
    }


    @Test
    @DisplayName("게시물을 페이지로 조회한다.")
    void test_find_pageable_post() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = new ArrayList<>();
        for (int i=0; i<20; i++) {
            posts.add(Post.builder().build());
        }
        given(postRepository.findAll(pageable)).willReturn(new PageImpl<>(posts));

        Page<PostResponse> findPosts = postService.findPosts(pageable);

        assertThat(findPosts).hasSize(20);
    }

    @Test
    @DisplayName("게시물을 단건 조회한다.")
    void test_find_post_by_id() throws Exception {
        PostResponse response = PostResponse.builder()
                .id(1L)
                .title("this is title")
                .content("thie is content")
                .userId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(converter.toPostResponse(post)).willReturn(response);

        PostResponse postResponse = postService.findPost(1L);

        then(postRepository).should().findById(1L);
        assertThat(postResponse.getId()).isEqualTo(1L);
    }
}

