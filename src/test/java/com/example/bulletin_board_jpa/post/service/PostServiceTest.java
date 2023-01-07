package com.example.bulletin_board_jpa.post.service;

import com.example.bulletin_board_jpa.post.PostRepository;
import com.example.bulletin_board_jpa.post.dto.PostRequestDto;
import com.example.bulletin_board_jpa.post.dto.PostResponseDto;
import com.example.bulletin_board_jpa.user.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void save() {
        // given
        UserDto userDto = new UserDto("이동준", 28, "기타 치기");
        PostRequestDto postRequestDto = new PostRequestDto("오늘의 일기", "즐거웠다", userDto);

        // when
        Long savedId = postService.save(postRequestDto);

        // then

        assertThat(savedId).isEqualTo(1L);

    }

    @AfterEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    void findOne() throws ChangeSetPersister.NotFoundException {
        // given
        Long postId = 1L;

        // when
        PostResponseDto one = postService.findOne(postId);

        // then
        assertThat(one.getTitle()).isEqualTo("오늘의 일기");
        assertThat(one.getContent()).isEqualTo("즐거웠다");
    }

    @Test
    void findAll() {
        // given
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<PostResponseDto> all = postService.findAll(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }
}