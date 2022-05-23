package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.dto.UserResponseDto;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Long postId;

    @BeforeEach
    void saveTest() {
        // given
        User user = userRepository.save(new User("name", 25, "hobby"));
        Long userId = user.getId();

        PostRequestDto postRequestDto = new PostRequestDto(
                "title",
                "content",
                new UserResponseDto(userId, "name", 25, "hobby"));


        // when
        postId = postService.writePost(postRequestDto).id();

        // then
        assertThat(postId).isGreaterThan(0);
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAllInBatch();
    }

    @Test
    void getOnePostTest() throws NotFoundException {
        // given

        // when
        PostResponseDto postResponseDto = postService.getOnePost(postId);

        // then
        assertThat(postResponseDto.id()).isEqualTo(postId);
    }

    @Test
    void getAllPostTest() {
        // given
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<PostResponseDto> all = postService.getAllPostByPage(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    void updatePostTest() throws NotFoundException {
        // given
        PostResponseDto postResponseDto = postService.getOnePost(postId);

        // when
        postService.updatePost(postResponseDto.id(), "new-Title", "new-Content");

        // then
        PostResponseDto updated = postService.getOnePost(postId);
        assertThat(updated.title()).isEqualTo("new-Title");
        assertThat(updated.content()).isEqualTo("new-Content");
    }
}