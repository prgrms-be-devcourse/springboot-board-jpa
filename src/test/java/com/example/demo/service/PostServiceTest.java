package com.example.demo.service;

import com.example.demo.dto.PostDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

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

    @AfterEach
    void deleteData() {
        postRepository.deleteAll();
    }

    @Test
    void findOneTest() throws PostNotFoundException {
        Long findPostId = postId;
        PostDto returnPostDto = postService.findOne(findPostId);
        assertThat(returnPostDto.id()).isEqualTo(findPostId);
    }

    @Test
    @DisplayName("없는 postId로 post를 찾으면 NotFoundException이 발생한다.")
    void findOneTestException() {
        Long findPostId = new Random().nextLong();
        assertThrows(PostNotFoundException.class, () -> postService.findOne(findPostId));
    }

    @Test
    void getAllByPageTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<PostDto> allByPage = postService.findAllByPage(pageRequest);
        assertThat(allByPage.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("post의 제목과 내용을 수정한다")
    void updateOne() throws PostNotFoundException {
        UserDto userDto = UserDto.builder()
                .name("change_name")
                .age(100)
                .hobby("hair change")
                .build();
        PostDto postDto = PostDto
                .builder()
                .title("this is changed title")
                .content("this is changed contents")
                .userDto(userDto)
                .build();
        PostDto updatedPost = postService.updateTitleAndContent(postDto, postId);

        assertThat(updatedPost.title()).isEqualTo("this is changed title");
        assertThat(updatedPost.updatedAt()).isNotNull();
    }
}