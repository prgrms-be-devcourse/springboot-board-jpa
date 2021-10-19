package com.toy.board.springbootboard.model.service;


import com.toy.board.springbootboard.model.converter.PostConverter;
import com.toy.board.springbootboard.model.domain.User;
import com.toy.board.springbootboard.model.dto.PostDto;
import com.toy.board.springbootboard.model.dto.UserDto;
import com.toy.board.springbootboard.model.repository.PostRepository;
import com.toy.board.springbootboard.model.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceTest {
    private UserRepository userRepository;

    private PostService postService;
    private final PostConverter postConverter;

    User user;
    PostDto postDto;

    @Autowired
    public PostServiceTest(UserRepository userRepository, PostService postService, PostConverter postConverter) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.postConverter = postConverter;
    }

    @BeforeAll
    void setUp() {
        user = postConverter.convertUser(UserDto.builder()
                .id(1L)
                .name("minkyu")
                .age(26)
                .hobby("programming")
                .build());
        postDto = PostDto.builder()
                .id(1L)
                .userId(1L)
                .title("testTitle1")
                .content("testContent1")
                .build();
        userRepository.save(user);
    }

    @Test
    @Order(1)
    @DisplayName("Post 생성, 조회 테스트")
    public void createPostTest() throws NotFoundException {
        postService.save(postDto);
        PostDto findPostDto = postService.findById(postDto.getId());

        assertThat(findPostDto.getId()).isEqualTo(postDto.getId());
        assertThat(findPostDto.getTitle()).isEqualTo(postDto.getTitle());
        assertThat(findPostDto.getContent()).isEqualTo(postDto.getContent());
        log.info("SV: {} ",postDto);
        log.info("DB: {} ",findPostDto);
    }

    @Test
    @Order(2)
    @DisplayName("Post 수정 테스트")
    public void UpdatePostTest() throws NotFoundException {
        PostDto updatedPostDto = PostDto.builder()
                .id(1L)
                .userId(1L)
                .title("updatedTestTitle1")
                .content("updatedTestContent1")
                .build();

        postService.update(1L,updatedPostDto);

        PostDto findUpdatedPostDto = postService.findById(1L);

        assertThat(findUpdatedPostDto.getId()).isEqualTo(updatedPostDto.getId());
        assertThat(findUpdatedPostDto.getTitle()).isEqualTo(updatedPostDto.getTitle());
        assertThat(findUpdatedPostDto.getContent()).isEqualTo(updatedPostDto.getContent());
        log.info("SV: {} ",updatedPostDto);
        log.info("DB: {} ",findUpdatedPostDto);
    }
}