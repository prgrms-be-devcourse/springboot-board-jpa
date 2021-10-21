package com.example.board.domain.post.service;

import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.domain.user.dto.UserDto;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    UserDto userDto;

    @BeforeEach
    void setup() {
        userDto = UserDto.builder()
                .name("이름")
                .age(20)
                .hobby("취미")
                .build();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void findOne() throws NotFoundException {
        PostDto postDto = PostDto.builder()
                .title("제목1")
                .content("내용입니다.")
                .userDto(userDto)
                .build();

        Long saveId = postService.save(postDto);

        PostDto one = postService.findOne(saveId);

        assertThat(one.getId()).isEqualTo(saveId);
    }

    @Test
    void findPosts() {
        PostDto postDto1 = PostDto.builder()
                .title("제목1")
                .content("내용입니다.")
                .userDto(userDto)
                .build();

        PostDto postDto2 = PostDto.builder()
                .title("제목2")
                .content("내용입니다.")
                .userDto(userDto)
                .build();


        postService.save(postDto1);
        postService.save(postDto2);

        Page<PostDto> posts = postService.findPosts(PageRequest.of(0, 10));

        assertThat(posts.getTotalElements()).isEqualTo(2);
    }
}