package com.kdt.springbootboardjpa.service;

import com.kdt.springbootboardjpa.converter.PostConverter;
import com.kdt.springbootboardjpa.domain.Post;
import com.kdt.springbootboardjpa.domain.User;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.domain.dto.PostDTO;
import com.kdt.springbootboardjpa.domain.dto.PostUpdateRequest;
import com.kdt.springbootboardjpa.exception.PostNotFoundException;
import com.kdt.springbootboardjpa.exception.UserNotFoundException;
import com.kdt.springbootboardjpa.repository.PostRepository;
import com.kdt.springbootboardjpa.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class MockPostServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PostConverter converter;

    @InjectMocks
    PostService postService;

    String title = "test-post";
    String content = "woooowo";

    User user = User.builder()
            .username("hj")
            .age(20)
            .hobby("sleeping")
            .build();

    Post post = Post.builder()
            .title(title)
            .content(content)
            .user(user)
            .build();

    PostDTO dto = new PostDTO(1L, title, content, "hj");
    PostUpdateRequest request = new PostUpdateRequest(title, content);

    @Test
    @DisplayName("게시글 검색 테스트")
    void findPostByIdTest() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(converter.convertPostDTO(post)).thenReturn(dto);

        var found = postService.findPost(1L);

        assertThat(found, is(samePropertyValuesAs(dto)));
    }

    @Test
    @DisplayName("게시글 엔티티 생성 테스트")
    void makePostEntityTest(){
        var request = new PostCreateRequest(title, content, "hj");
        when(userRepository.findByUsername("hj")).thenReturn(Optional.of(user));
        when(converter.convertPost(request, user)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);

        postService.makePost(request);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    @DisplayName("다수 게시글 생성 테스트")
    void makeMultiplePostsTest(){

        //given
        var p1 = new Post("post1", "--", user);
        var p2 = new Post("post2", "--", user);
        var p3 = new Post("post3", "--", user);

        new PostDTO(1L, "title", "content", "username");
        var dto1 = new PostDTO(1, "post1", "--", user.getUsername());
        var dto2 = new PostDTO(2, "post2", "--", user.getUsername());
        var dto3 = new PostDTO(3, "post3", "--", user.getUsername());

        when(converter.convertPostDTO(p1)).thenReturn(dto1);
        when(converter.convertPostDTO(p2)).thenReturn(dto2);
        when(converter.convertPostDTO(p3)).thenReturn(dto3);

        Page<Post> posts = new PageImpl<>(List.of(p1, p2, p3));

        var pageble = PageRequest.of(1, 10);
        when(postRepository.findAllWithFetch(pageble)).thenReturn(posts);

        //when
        var found = postService.findAllPosts(pageble);

        //then
        assertThat(found.getTotalElements(), is(3L));
        assertThat(found, samePropertyValuesAs(new PageImpl<>(List.of(dto1, dto2, dto3))));
    }

    @Test
    @DisplayName("Post 수정 테스트")
    void editPostTest(){
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        var newRequest = new PostUpdateRequest("new-title", "new-content");
        postService.editPost(1L, newRequest);
        verify(postRepository).findById(1L);
    }

    @Test
    @DisplayName("PostNotFoundException 테스트")
    void pageNotFoundTest(){
        //given
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.findPost(2L));
        assertThrows(PostNotFoundException.class, () -> postService.editPost(1L, request));
    }

    @Test
    @DisplayName("UserNotFoundException 테스트")
    void userNotFoundTest(){

        //given
        var request = new PostCreateRequest("exception-title", "--", "throwww");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> postService.makePost(request));
    }
}