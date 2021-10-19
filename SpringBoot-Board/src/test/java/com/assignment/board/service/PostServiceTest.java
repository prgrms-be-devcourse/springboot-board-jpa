package com.assignment.board.service;

import com.assignment.board.domain.Post;
import com.assignment.board.domain.User;
import com.assignment.board.dto.DtoConverter;
import com.assignment.board.dto.PostDto;
import com.assignment.board.dto.UserDto;
import com.assignment.board.repository.PostRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    DtoConverter dtoConverter;

    @BeforeEach
    void save_test() {
        User user = DtoConverter.convertUser(UserDto.builder()
                .name("NaSangwon")
                .age(28)
                .hobby("빈둥거리기")
                .build());
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("테스트하기")
                .content("서비스 테스트 입니다.")
                .userDto(
                        UserDto.builder()
                                .name("NaSangwon")
                                .age(28)
                                .hobby("빈둥거리기")
                                .build()
                )
                .build();

        Post post = dtoConverter.convertPost(postDto);

        Long savedId = postService.save(postDto);

    }

    @Test
    @Transactional
    void findOneTest() throws NotFoundException {
        // Given
        Long postId = 1L;

        //when
        PostDto one = postService.findOne(postId);

        //Then
        assertThat(one.getId()).isEqualTo(postId);
    }

    @Test
    @Transactional
    void findAllTest() {
        // Given
        PageRequest page = PageRequest.of(0, 10);

        // When
        Page<PostDto> all = postService.findAll(page);

        // Then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }
}
