package com.programmers.board.service;

import com.programmers.board.dto.PostDto;
import com.programmers.board.dto.UserDto;
import com.programmers.board.repository.PostRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.h2.pagestore.db.PageDataIndex;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private Long id = 1L;

    @BeforeEach
    void save() {
        // Given
        PostDto postDto = PostDto.builder()
                .id(id)
                .title("여기에는 게시물 제목")
                .content("여기에는 게시물 내용")
                .userDto(
                        UserDto.builder()
                                .name("오재욱")
                                .age(28)
                                .hobby("영화 감상")
                                .build()
                ).build();

        // When
        Long savedId = postService.save(postDto);

        // Then
        assertThat(savedId).isEqualTo(id);
    }

    @AfterEach
    void cleanup(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("단일 조회를 테스트합니다.")
    void findByIdTest() throws NotFoundException {
        // Given
        Long postId = id;

        // When
        PostDto postDto = postService.findOneById(postId);

        // Then
        assertThat(postDto.getId()).isEqualTo(postId);
    }

    @Test
    @DisplayName("전체 조회를 테스트합니다.")
    void findAllTest() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // When
        Page<PostDto> all = postService.findAll(pageRequest);

        // Then
        assertThat(all.getTotalElements()).isEqualTo(id);

    }

//    @Test
//    @DisplayName("수정 작업을 테스트합니다.")
//    void updateTest() {
//    }
}