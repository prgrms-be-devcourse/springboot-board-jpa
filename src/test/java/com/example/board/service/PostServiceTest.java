package com.example.board.service;

import com.example.board.dto.PostRequest;
import com.example.board.dto.PostResponse;
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
        PostRequest request = PostRequest.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .build();

        // when
        Long savedPostId = postService.save(request);
        PostResponse foundPostDto = postService.findById(savedPostId);

        // then
        assertThat(foundPostDto.getTitle()).isEqualTo(request.getTitle());
        assertThat(foundPostDto.getContent()).isEqualTo(request.getContent());
    }

    @Test
    @DisplayName("전체 게시글 조회가 정상적으로 이루어진다")
    void findAllPost() {
        // given
        PostRequest request1 = PostRequest.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .build();

        PostRequest request2 = PostRequest.builder()
                .title("Test Post 2")
                .content("Content of Test Post 2")
                .build();

        PostRequest request3 = PostRequest.builder()
                .title("Test Post 3")
                .content("Content of Test Post 3")
                .build();

        postService.save(request1);
        postService.save(request2);
        postService.save(request3);

        // when
        PageRequest page = PageRequest.of(0, 10);
        Page<PostResponse> all = postService.findAll(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("존재하지 않는 ID의 게시글 조회시 지정한 예외가 정상적으로 발생한다")
    void findWithWrongID() {
        // given
        PostRequest request = PostRequest.builder()
                .title("temp")
                .content("temp")
                .build();
        postService.save(request);

        // when
        Long wrongId = -1L;

        // then
        assertThrows(PostNotFoundException.class, () -> {
            postService.findById(wrongId);
        });
    }

}