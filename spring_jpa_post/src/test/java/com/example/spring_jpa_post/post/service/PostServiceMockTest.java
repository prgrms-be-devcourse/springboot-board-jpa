package com.example.spring_jpa_post.post.service;

import com.example.spring_jpa_post.post.dto.request.CreatePostRequest;
import com.example.spring_jpa_post.post.dto.request.ModifyPostRequest;
import com.example.spring_jpa_post.post.dto.response.FoundPostResponse;
import com.example.spring_jpa_post.post.entity.Post;
import com.example.spring_jpa_post.post.repository.PostRepository;
import com.example.spring_jpa_post.user.entity.User;
import com.example.spring_jpa_post.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceMockTest {
    @InjectMocks
    PostService postService;
    @Mock
    PostRepository postRepository;
    @Mock
    PostMapper postMapper;
    @Mock
    UserRepository userRepository;

    Post post;

    @BeforeEach
    void setup() {
        post = Post.builder().title("title").content("content").build();
    }

    @Test
    @DisplayName("게시판을 생성할 수 있다.")
    void createPostTest() {
        //given
        CreatePostRequest createPostRequest = CreatePostRequest.builder().title("title").content("content").userId(1L).build();
        given(userRepository.findById(any())).willReturn(Optional.ofNullable(User.builder().age(1).name("용").build()));
        given(postMapper.toPostFromCreatePostRequest(any())).willReturn(post);
        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(post));
        //when
        Long savedPostId = postService.createPost(createPostRequest);
        //then
        Optional<Post> findPost = postRepository.findById(savedPostId);
        assertThat(findPost).isPresent();
        assertThat(findPost.get()).usingRecursiveComparison().isEqualTo(post);
    }

    @Test
    @DisplayName("게시글을 모두 조회할 수 있다.")
    void getAllPostTest() {
        //given
        PageRequest testPagRequest = PageRequest.of(0, 10);
        Page<Post> posts = new PageImpl<>(List.of(post));
        given(postRepository.findAll(any(Pageable.class))).willReturn(posts);
        //when
        Page<FoundPostResponse> foundAllPost = postService.getAllPost(testPagRequest);
        //then
        assertThat(foundAllPost).isNotEmpty();
        assertThat(foundAllPost.getSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 게시글을 조회할 수 있다.")
    void getPostByIdTest() {
        //given
        given(postRepository.findById(any())).willReturn(Optional.of(post));
        given(postMapper.toFoundResponseFromPost(any())).willReturn(FoundPostResponse.builder().build());
        //when
        FoundPostResponse findDto = postService.getPostById(anyLong());
        //then
        assertThat(findDto).isNotNull();
    }

    @Test
    @DisplayName("게시글을 수정할 수있다")
    void modifyPostTest() {
        //given
        String updateTitle = "update_Title";
        String updateContent = "update_Content";
        ModifyPostRequest modifyPostRequest = ModifyPostRequest.builder().title(updateTitle).content(updateContent).build();
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(post));
        //when
        Long modifiedPostId = postService.modifyPost(anyLong(),modifyPostRequest);
        //then
        Post foundPost = postRepository.findById(modifiedPostId).get();
        assertThat(foundPost.getTitle()).isEqualTo(updateTitle);
        assertThat(foundPost.getContent()).isEqualTo(updateContent);
    }
}