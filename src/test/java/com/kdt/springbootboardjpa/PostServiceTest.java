package com.kdt.springbootboardjpa;

import com.kdt.springbootboardjpa.converter.PostConverter;
import com.kdt.springbootboardjpa.domain.Post;
import com.kdt.springbootboardjpa.domain.User;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.domain.dto.PostDTO;
import com.kdt.springbootboardjpa.repository.PostRepository;
import com.kdt.springbootboardjpa.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

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

    @Test
    @DisplayName("Post 검색 테스트")
    void findPostByIdTest() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(converter.convertPostDTO(post)).thenReturn(dto);

        var found = postService.findPost(1L);

        assertThat(found, is(samePropertyValuesAs(dto)));
    }

    @Test
    @DisplayName("Entity Post 생성 테스트")
    void makePostEntityTest(){
        var request = new PostCreateRequest(title, content, "hj");
        when(userRepository.findByUsername("hj")).thenReturn(Optional.of(user));
        when(converter.convertPost(request, user)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);

        postService.makePost(request);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    @DisplayName("Post 수정 테스트")
    void editPostTest(){
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        var newDTO = new PostDTO(1L, "new-title", "new-content", user.getUsername());
        postService.editPost(1L, newDTO);
        verify(postRepository).findById(1L);
    }

}