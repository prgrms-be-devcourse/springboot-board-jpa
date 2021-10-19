package com.example.board.service;

import com.example.board.dto.PostDto;
import com.example.board.exception.PostNotFoundException;
import com.example.board.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("(Controller에서 전달받은) 게시글을 정상적으로 저장한다.")
    void savePost() {
        // given
        PostDto postDto = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .build();

        // when
        Long savedPostId = postService.save(postDto);
        PostDto foundPostDto = postService.findById(savedPostId);

        // then
        assertThat(foundPostDto.getTitle()).isEqualTo(postDto.getTitle());
        assertThat(foundPostDto.getContent()).isEqualTo(postDto.getContent());
    }

    @Test
    @DisplayName("전체 게시글 조회가 정상적으로 이루어진다")
    void findAllPost() {
        // given
        PostDto postDto1 = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .build();

        PostDto postDto2 = PostDto.builder()
                .title("Test Post 2")
                .content("Content of Test Post 2")
                .build();

        PostDto postDto3 = PostDto.builder()
                .title("Test Post 3")
                .content("Content of Test Post 3")
                .build();

        postService.save(postDto1);
        postService.save(postDto2);
        postService.save(postDto3);

        // when
        PageRequest page = PageRequest.of(0, 10);
        Page<PostDto> all = postService.findAll(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("존재하지 않는 ID의 게시글 조회시 지정한 예외가 정상적으로 발생한다")
    void findWithWrongID() {
        // given
        PostDto postDto = PostDto.builder()
                .title("temp")
                .content("temp")
                .build();
        postService.save(postDto);

        // when
        Long wrongId = -1L;

        // then
        assertThrows(PostNotFoundException.class, () -> {
            postService.findById(wrongId);
        });
    }

}