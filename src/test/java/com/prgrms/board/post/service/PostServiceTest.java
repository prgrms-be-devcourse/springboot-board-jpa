package com.prgrms.board.post.service;

import com.prgrms.board.domain.post.PostRepository;
import com.prgrms.board.post.dto.PostDto;
import com.prgrms.board.post.dto.UserDto;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private PostRepository postRepository;

    private UserDto userDto;
    private PostDto postDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1L)
                .name("이범진")
                .age(33)
                .hobby("독서")
                .build();

        postDto = PostDto.builder()
                .id(1L)
                .title("게시글 제목")
                .content("게시글 내용")
                .userDto(userDto)
                .build();

        Long postDtoId = postService.save(postDto);

        assertThat(postRepository.findById(postDtoId)).isNotEmpty();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 저장 테스트")
    @Test
    void savePost() throws NotFoundException {
        //Given
        userDto = UserDto.builder()
                .id(1L)
                .name("이범진")
                .age(33)
                .hobby("독서")
                .build();

        postDto = PostDto.builder()
                .id(1L)
                .title("게시글 제목")
                .content("게시글 내용")
                .userDto(userDto)
                .build();

        postService.save(postDto);

        assertThat(postRepository.findAll()).isNotEmpty();
    }

    @DisplayName("게시물 단건 조회 테스트")
    @Test
    void findOnePost() throws NotFoundException {
        //when
        PostDto findPost = postService.findOne(1L);

        //then
        assertThat(findPost.getId()).isEqualTo(findPost.getId());
        assertThat(findPost.getTitle()).isEqualTo(findPost.getTitle());
        assertThat(findPost.getContent()).isEqualTo(findPost.getContent());
    }

    @DisplayName("게시물 다건 조회 테스트")
    @Test
    void findAllPosts() {
        //given
        PageRequest page = PageRequest.of(0, 10);

        //when
        Page<PostDto> pageAll = postService.findAll(page);

        //then
        assertThat(pageAll.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    void updatePost() throws NotFoundException {
        //given
        postDto.changeTitle("수정된 제목");
        postDto.changeContent("수정된 게시글");

        //when
        postService.update(postDto);

        //then
        PostDto findPost = postService.findOne(1L);
        assertThat(findPost.getId()).isEqualTo(1L);
    }
}