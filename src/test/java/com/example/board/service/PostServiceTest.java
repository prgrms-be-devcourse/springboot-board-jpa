package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import com.example.board.repository.UserRepository;
import org.apache.ibatis.javassist.NotFoundException;
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

    private Long postId;

    @BeforeEach
    void saveTest() {
        // given
        User user = userRepository.save(new User("name", 25, "hobby"));
        Long userId = user.getId();

        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .author(UserDto.builder()
                        .id(userId)
                        .name("name")
                        .age(25)
                        .hobby("hobby")
                        .build())
                .build();

        // when
        postId = postService.writePost(postDto);

        // then
        assertThat(postId).isGreaterThan(0);
    }

    @Test
    void getOnePostTest() throws NotFoundException {
        // given

        // when
        PostDto postDto = postService.getOnePost(postId);

        // then
        assertThat(postDto.getId()).isEqualTo(postId);
    }

    @Test
    void getAllPostTest() {
        // given
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<PostDto> all = postService.getAllPost(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    void updatePostTest() throws NotFoundException {
        // given
        PostDto postDto = postService.getOnePost(postId);

        // when
        postService.updatePost(postDto.getId(), "new-Title", "new-Content");

        // then
        PostDto updated = postService.getOnePost(postId);
        assertThat(updated.getTitle()).isEqualTo("new-Title");
        assertThat(updated.getContent()).isEqualTo("new-Content");
    }
}