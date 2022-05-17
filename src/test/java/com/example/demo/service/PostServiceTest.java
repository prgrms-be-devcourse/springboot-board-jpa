package com.example.demo.service;

import com.example.demo.dto.PostDto;
import com.example.demo.dto.UserDto;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    private Long postId;

    @BeforeEach
    void save_test() {
        //Given
        UserDto userDto = UserDto.builder()
                .name("jamie")
                .age(34)
                .hobby("dance")
                .build();
        PostDto postDto = PostDto.builder()
                .title("this is title")
                .content("this is contents")
                .userDto(userDto)
                .build();
        //When
        postId = postService.save(postDto);
        //Then
        assertThat(postId).isNotZero();
    }

    @Test
    void findOneTest() throws NotFoundException {
        Long findPostId = postId;
        PostDto returnPostDto = postService.findOne(findPostId);
        assertThat(returnPostDto.getId()).isEqualTo(findPostId);
    }

    @Test
    @DisplayName("없는 postId로 post를 찾으면 NotFoundException이 발생한다.")
    void findOneTestException() {
        Long findPostId = new Random().nextLong();
        assertThrows(NotFoundException.class, () -> postService.findOne(findPostId));
    }
}