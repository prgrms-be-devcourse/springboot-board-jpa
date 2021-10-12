package com.homework.springbootboard.service;

import com.homework.springbootboard.converter.PostConverter;
import com.homework.springbootboard.dto.PostDto;
import com.homework.springbootboard.dto.UserDto;
import com.homework.springbootboard.model.Post;
import com.homework.springbootboard.model.User;
import com.homework.springbootboard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostConverter postConverter;

    PostDto postDto;

    @BeforeEach
    void setUp() {
        postDto = PostDto.builder()
                .id(2L)
                .title("testTitle")
                .content("testContent")
                .userDto(
                        UserDto.builder()
                                .id(1L)
                                .name("testUser")
                                .age(28)
                                .hobby("testHobby")
                                .build()
                )
                .build();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물을 저장할 수 있다.")
    void save() {
        // when
        Long save = postService.save(postDto);

        // then
        assertThat(postRepository.findById(save).get().getId()).isEqualTo(save);
        assertThat(postRepository.findById(save).get().getTitle()).isEqualTo(postDto.getTitle());
    }

    @Test
    @DisplayName("게시물을 수정할 수 있다.")
    void update() {
        // given
        Long save = postService.save(postDto);
        PostDto updateDto = PostDto.builder()
                .title("updateTitle")
                .content("updateContent")
                .userDto(
                        UserDto.builder()
                                .id(1L)
                                .name("testUser")
                                .age(28)
                                .hobby("testHobby")
                                .build()
                )
                .build();

        // when
        Long update = postService.update(save, updateDto);

        // then
        assertThat(postRepository.findById(update).get().getTitle()).isEqualTo("updateTitle");
        assertThat(postRepository.findById(update).get().getContent()).isEqualTo("updateContent");
    }

    @Test
    @DisplayName("게시물을 단건으로 조회할 수 있다.")
    void findPost() {
        // given
        Long saveId = postService.save(postDto);

        // when
        PostDto findPostDto = postService.findPost(saveId);
        Post findPost = postConverter.convertPost(findPostDto);

        // then
        assertThat(findPost.getId()).isEqualTo(2L);
        assertThat(findPost.getTitle()).isEqualTo("testTitle");
        assertThat(findPost.getContent()).isEqualTo("testContent");
    }

    @Test
    @DisplayName("게시물을 페이징 처리해서 조회할 수 있다.")
    void findPosts() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        givenPostList();

        // when
        Page<PostDto> all = postService.findPosts(pageRequest);

        // then
        assertThat(all.getTotalPages()).isEqualTo(2);
        assertThat(all.getTotalElements()).isEqualTo(20);
    }

    private void givenPostList() {
        IntStream.range(2, 22).mapToObj(i -> PostDto.builder()
                .id((long) i)
                .title("testTitle")
                .content("testContent")
                .userDto(
                        UserDto.builder()
                                .id(1L)
                                .name("testUser")
                                .age(28)
                                .hobby("testHobby")
                                .build()
                )
                .build()).forEach(build -> postService.save(build));
    }
}