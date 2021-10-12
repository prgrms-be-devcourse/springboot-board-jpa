package com.example.board.service;

import com.example.board.dto.PostDto;
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
        PostDto postDto = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .createdBy("Test User Name 1")
                .build();

        Long savedPostId = postService.save(postDto);
        PostDto foundPostDto = postService.findById(savedPostId);

        assertThat(foundPostDto.getTitle()).isEqualTo(postDto.getTitle());
        assertThat(foundPostDto.getContent()).isEqualTo(postDto.getContent());
        assertThat(foundPostDto.getCreatedBy()).isEqualTo(postDto.getCreatedBy());
    }

    @Test
    @DisplayName("전체 게시글 조회가 정상적으로 이루어진다")
    void findAllPost() {
        PostDto postDto1 = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .createdBy("Test User Name 1")
                .build();
        PostDto postDto2 = PostDto.builder()
                .title("Test Post 2")
                .content("Content of Test Post 2")
                .createdBy("Test User Name 2")
                .build();
        PostDto postDto3 = PostDto.builder()
                .title("Test Post 3")
                .content("Content of Test Post 3")
                .createdBy("Test User Name 3")
                .build();
        postService.save(postDto1);
        postService.save(postDto2);
        postService.save(postDto3);

        PageRequest page = PageRequest.of(0, 10);

        Page<PostDto> all = postService.findAll(page);

        assertThat(all.getTotalElements()).isEqualTo(3);
    }
}