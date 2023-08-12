package com.programmers.springbootboardjpa.domain.post.service;

import com.programmers.springbootboardjpa.domain.post.dto.PostCreateRequestDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostResponseDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostUpdateRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserResponseDto;
import com.programmers.springbootboardjpa.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    private PostCreateRequestDto postCreateRequestDto;

    private PostUpdateRequestDto postUpdateRequestDto;

    private Long userId;

    @BeforeEach
    void setUp() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("김이름")
                .age(27)
                .hobby("캠핑")
                .build();

        UserResponseDto userResponseDto = userService.create(userRequestDto);
        userId = userResponseDto.id();

        postCreateRequestDto = PostCreateRequestDto.builder()
                .title("등록용 제목")
                .content("등록용 내용")
                .userId(userId)
                .build();

        postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("수정용 제목")
                .content("수정용 내용")
                .build();
    }

    @DisplayName("게시글을 저장한다")
    @Test
    void create() {
        //given
        //when
        PostResponseDto postResponseDto = postService.create(postCreateRequestDto);
        PostResponseDto result = postService.findById(postResponseDto.id());

        //then
        assertThat(result.title()).isEqualTo(postCreateRequestDto.title());
        assertThat(result.content()).isEqualTo(postCreateRequestDto.content());
        assertThat(result.userId()).isEqualTo(postCreateRequestDto.userId());
    }

    @DisplayName("게시글을 수정한다")
    @Test
    void update() {
        //given
        PostResponseDto savedPostResponseDto = postService.create(postCreateRequestDto);

        Long requestPostId = savedPostResponseDto.id();

        //when
        PostResponseDto postResponseDto = postService.update(requestPostId, postUpdateRequestDto);

        //then
        assertThat(postResponseDto.title()).isEqualTo(postUpdateRequestDto.title());
        assertThat(postResponseDto.content()).isEqualTo(postUpdateRequestDto.content());
    }

    @DisplayName("id로 게시글을 단건 조회한다")
    @Test
    void findById() {
        //given
        PostResponseDto savedPostResponseDto = postService.create(postCreateRequestDto);

        Long requestPostId = savedPostResponseDto.id();

        //when
        PostResponseDto postResponseDto = postService.findById(requestPostId);

        //then
        assertThat(postResponseDto.title()).isEqualTo(savedPostResponseDto.title());
        assertThat(postResponseDto.content()).isEqualTo(savedPostResponseDto.content());
        assertThat(postResponseDto.userId()).isEqualTo(savedPostResponseDto.userId());
    }

    @DisplayName("저장된 게시글들을 페이징 조회한다")
    @Test
    void findAll() {
        //given
        postService.create(postCreateRequestDto);

        PostCreateRequestDto postCreateRequestDto2 = PostCreateRequestDto.builder()
                .title("등록용 제목")
                .content("등록용 내용")
                .userId(userId)
                .build();

        postService.create(postCreateRequestDto2);

        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        Page<PostResponseDto> result = postService.findAll(pageRequest);

        //then
        assertThat(result).hasSize(2);
    }
}