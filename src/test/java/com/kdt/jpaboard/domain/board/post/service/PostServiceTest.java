package com.kdt.jpaboard.domain.board.post.service;

import com.kdt.jpaboard.domain.board.post.dto.CreatePostDto;
import com.kdt.jpaboard.domain.board.post.dto.PostDto;
import com.kdt.jpaboard.domain.board.post.dto.UpdatePostDto;
import com.kdt.jpaboard.domain.board.user.dto.CreateUserDto;
import com.kdt.jpaboard.domain.board.user.dto.UserDto;
import com.kdt.jpaboard.domain.board.user.service.UserService;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    private CreateUserDto userDto;
    private CreatePostDto createPostDto;
    private Long userId;

    @BeforeEach
    void setup() {

        userDto = CreateUserDto.builder()
                .name("beomsic")
                .age(26)
                .hobby("soccer")
                .build();

        userId = userService.save(userDto);

        createPostDto = CreatePostDto.builder()
                        .title("테스트")
                        .content("테스트")
                        .build();

    }

    @AfterEach
    void reset() {}

    @Test
    @DisplayName("게시물 작성 테스트")
    void testSave() throws NotFoundException {
        // Given
        UserDto saveUser = userService.findById(userId);
        createPostDto.setUserDto(saveUser);

        // When
        postService.save(createPostDto);

        // Then
        String name = createPostDto.getUserDto().getName();
        assertThat(name.equals(saveUser.getName()), is(true));
    }

    @Test
    @DisplayName("게시물 페이징 조회 테스트")
    void testFindAll() throws NotFoundException {
        // Given
        PageRequest page = PageRequest.of(0, 10);
        UserDto saveUser = userService.findById(userId);
        createPostDto.setUserDto(saveUser);

        // When
        postService.save(createPostDto);
        Page<PostDto> all = postService.findAll(page);

        // Then
        assertThat(all.getTotalElements() == 1, is(true));
    }

    @Test
    @DisplayName("게시물 단건 조회 테스트")
    void testFindOne() throws NotFoundException {
        // Given
        UserDto saveUser = userService.findById(userId);
        createPostDto.setUserDto(saveUser);

        // When
        Long postId = postService.save(createPostDto);
        PostDto one = postService.findById(postId);

        // Then
        assertThat(postId.equals(one.getPostId()), is(true));
        assertThat(one.getUserDto().getName().equals("beomsic"), is(true));
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void testUpdate() throws NotFoundException {
        // Given
        UserDto saveUser = userService.findById(userId);
        createPostDto.setUserDto(saveUser);

        // When
        Long postId = postService.save(createPostDto);
        PostDto findPost = postService.findById(postId);

        UpdatePostDto updatePostDto = new UpdatePostDto(findPost.getTitle(), findPost.getContent());
        updatePostDto.setTitle("수정 테스트");
        Long update = postService.update(findPost.getPostId(), updatePostDto);

        // Then
        PostDto one = postService.findById(update);
        assertThat(Objects.equals(update, postId), is(true));
        assertThat(Objects.equals(update, one.getPostId()), is(true));
    }
}