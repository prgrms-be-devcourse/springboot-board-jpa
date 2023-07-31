package com.programmers.domain.post.application;

import com.programmers.domain.post.ui.dto.PostDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import com.programmers.domain.user.application.UserService;
import com.programmers.domain.user.ui.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceImplTest {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;


    @Test
    void createPost() {
        // given
        UserDto userDto = new UserDto("name", 25, "hobby");
        userService.createUser(userDto);
        PostDto postDto = new PostDto("title", "content", 1L);

        // when
        Long savedPostId = postService.createPost(postDto);

        // then
        assertThat(savedPostId).isNotNull();
    }

    @Test
    void findPost() {
        // Given
        UserDto userDto = new UserDto("name", 25, "hobby");
        userService.createUser(userDto);

        PostDto postDto = new PostDto("title", "content", 1L);
        Long savedPostId = postService.createPost(postDto);

        // When
        PostDto postById = postService.findPost(1L);

        // Then
        assertThat(postById.title()).isEqualTo("title");

    }

    @Test
    void findAll() {
        // given
        UserDto userDto = new UserDto("name", 25, "hobby");
        userService.createUser(userDto);

        PostDto postDto = new PostDto("title", "content", 1L);
        PostDto postDto2 = new PostDto("제목", "내용", 1L);
        postService.createPost(postDto);
        postService.createPost(postDto2);

        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));

        // when
        List<PostDto> posts = postService.findAll(pageRequest);

        // then
        assertThat(posts.size()).isEqualTo(2);
    }

    @Test
    void updatePost() {
        // given
        UserDto userDto = new UserDto("name", 25, "hobby");
        userService.createUser(userDto);

        PostDto postDto = new PostDto("title", "content", 1L);
        Long savedPostId = postService.createPost(postDto);

        PostUpdateDto postUpdateDto = new PostUpdateDto("제목", "내용");

        // when
        PostDto updatedPost = postService.updatePost(postUpdateDto, savedPostId);

        // then
        assertThat(updatedPost.title()).isEqualTo(postUpdateDto.title());

    }
}