package com.kdt.springbootboard.service;

import com.kdt.springbootboard.dto.PostDto;
import com.kdt.springbootboard.dto.UserDto;
import com.kdt.springbootboard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    PostDto postDto;

    @BeforeEach
    void setUp() {
        postDto = PostDto.builder()
                .id(4L)
                .title("백엔드 데브코스")
                .content("정말 좋아요")
                .userDto(
                        UserDto.builder()
                                .id(1L)
                                .name("suebeen")
                                .age(25)
                                .hobby("bowling")
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
        assertThat(postDto.getId()).isEqualTo(save);
    }

    @Test
    @DisplayName("게시물을 수정할 수 있다.")
    void update() {
        // given
        Long save = postService.save(postDto);
        PostDto updateDto = PostDto.builder()
                .id(postDto.getId())
                .title("Back-End DevCourse")
                .content("very good!!")
                .userDto(
                        UserDto.builder()
                                .id(1L)
                                .name("suebeen")
                                .age(25)
                                .hobby("bowling")
                                .build()
                )
                .build();

        // when
        Long update = postService.update(save, updateDto);

        // then
        assertThat(postRepository.findById(update).get().getTitle()).isEqualTo("Back-End DevCourse");
        assertThat(postRepository.findById(update).get().getContent()).isEqualTo("very good!!");
    }

    @Test
    @DisplayName("게시물을 아이디로 조회할 수 있다.")
    void findPost() {
        // given
        Long save = postService.save(postDto);

        // when
        PostDto findPostDto = postService.findPost(save);

        // then
        assertThat(findPostDto.getId()).isEqualTo(save);
    }

    @Test
    @DisplayName("게시물을 페이징 처리해서 조회할 수 있다.")
    void findPosts() {
        // given
        PageRequest page = PageRequest.of(0, 10);
        Long save = postService.save(postDto);

        // When
        Page<PostDto> all = postService.findPosts(page);

        // Then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시물을 아이디로 삭제할 수 있다.")
    void deletePost() {
        // given
        Long save = postService.save(postDto);

        // when
        postService.deletePost(save);

        // then
        assertThat(postRepository.count()).isEqualTo(0);
    }
}


