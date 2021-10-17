package org.programmers.project_board.service;

import org.junit.jupiter.api.*;
import org.programmers.project_board.dto.PostDto;
import org.programmers.project_board.dto.UserDto;
import org.programmers.project_board.entity.Post;
import org.programmers.project_board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1L)
                .name("yekyeong")
                .age(23)
                .hobby("watching netflix")
                .build();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 게시글을 불러올 수 있다")
    void getAllPosts() {
        // given
        PostDto postDto = PostDto.builder()
                .title("title1")
                .content("content1")
                .userDto(userDto)
                .build();
        PostDto postDto2 = PostDto.builder()
                .title("title2")
                .content("content2")
                .userDto(userDto)
                .build();

        postService.savePost(postDto);
        postService.savePost(postDto2);

        // when
        List<PostDto> allPosts = postService.getAllPosts();

        // then
        assertThat(allPosts.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("원하는 id값을 가진 게시글을 불러올 수 있다")
    void getPost() {
        // given
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .userDto(userDto)
                .build();

        Long savedId = postService.savePost(postDto);

        // when
        PostDto findPostDto = postService.getPost(savedId);

        // then
        assertThat(findPostDto).isNotNull();
        assertThat(findPostDto.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("게시글을 저장할 수 있다")
    void savePost() {
        // given
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .userDto(userDto)
                .build();

        // when
        Long savedId = postService.savePost(postDto);

        // then
        Optional<Post> actual = postRepository.findById(savedId);
        assertThat(actual).isNotNull();
        assertThat(savedId).isEqualTo(actual.get().getId());
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다")
    void updatePost() {
        // given
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .userDto(userDto)
                .build();

        Long postId = postService.savePost(postDto);

        PostDto updatedPostDto = PostDto.builder()
                .title("updatedTitle")
                .content("updatedContent")
                .userDto(userDto)
                .build();

        // when
        Long id = postService.updatePost(postId, updatedPostDto);

        // then
        Optional<Post> actual = postRepository.findById(id);
        assertThat(actual).isNotNull();
        assertThat(actual.get().getTitle()).isEqualTo(updatedPostDto.getTitle());
        assertThat(actual.get().getContent()).isEqualTo(updatedPostDto.getContent());
    }

}