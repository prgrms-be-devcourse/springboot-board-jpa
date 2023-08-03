package com.programmers.domain.post.application;

import com.programmers.domain.post.application.converter.PostConverter;
import com.programmers.domain.post.ui.dto.PostDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import com.programmers.domain.user.application.UserService;
import com.programmers.domain.user.application.UserServiceImpl;
import com.programmers.domain.user.application.converter.UserConverter;
import com.programmers.domain.user.ui.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(value = {
        PostServiceImpl.class,
        UserServiceImpl.class,
        PostConverter.class,
        UserConverter.class})
class PostServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Test
    void createPost() {
        // given
        UserDto userDto = new UserDto("name", 25, "hobby");
        Long userId = userService.createUser(userDto);
        PostDto postDto = new PostDto("title", "content", userId);

        // when
        Long savedPostId = postService.createPost(postDto);

        // then
        assertThat(savedPostId).isNotNull();
    }

    @Test
    void findPost() {
        // Given
        UserDto userDto = new UserDto("name", 25, "hobby");
        Long userId = userService.createUser(userDto);

        PostDto postDto = new PostDto("title", "content", userId);
        Long postId = postService.createPost(postDto);

        // When
        PostDto post = postService.findPost(postId);

        // Then
        assertThat(post).isNotNull();
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
        int expectedCount = 2;
        assertThat(posts).hasSize(expectedCount);
    }

    @Test
    void updatePost() {
        // given
        UserDto userDto = new UserDto("name", 25, "hobby");
        Long userId = userService.createUser(userDto);

        PostDto postDto = new PostDto("title", "content", userId);
        Long savedPostId = postService.createPost(postDto);

        PostUpdateDto postUpdateDto = new PostUpdateDto("제목", "내용");

        // when
        PostDto updatedPost = postService.updatePost(postUpdateDto, savedPostId);

        // then
        assertThat(updatedPost.title()).isEqualTo(postUpdateDto.title());
        assertThat(updatedPost.content()).isEqualTo(postUpdateDto.content());
    }
}
