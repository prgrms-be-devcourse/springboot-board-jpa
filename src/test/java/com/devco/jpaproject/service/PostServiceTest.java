package com.devco.jpaproject.service;

import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostResponseDto;
import com.devco.jpaproject.domain.Post;
import com.devco.jpaproject.domain.User;
import com.devco.jpaproject.exception.UserNotFoundException;
import com.devco.jpaproject.repository.PostRepository;
import com.devco.jpaproject.repository.UserRepository;
import com.devco.jpaproject.service.converter.Converter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Converter converter;

    @Mock
    PostRequestDto postRequestDto;

    @Mock
    PostResponseDto postResponseDto;

    Post post;
    User writer;

    @BeforeEach
    void setUp() {
        writer = User.builder()
                .age(213)
                .name("jihun")
                .hobby("hhho")
                .id(1L)
                .posts(new ArrayList<>())
                .build();

        post = Post.builder()
                .writer(writer)
                .content("content1")
                .title("title")
                .id(1L)
                .build();

        writer.addPost(post);

        log.info("test writer id: {} and post id {}", writer.getId(), post.getId());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글을 삽입할 수 있다.")
    void insertTest() throws UserNotFoundException {
        //given
        postRequestDto = PostRequestDto.builder()
                .writerId(writer.getId())
                .content("content-tset")
                .title("title-test")
                .build();

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(writer));
        when(converter.toPostEntity(postRequestDto, writer)).thenReturn(post);
        when(postRepository.save(any())).thenReturn(post);
        var savedPostId = postService.insert(postRequestDto);

        //then
        assertThat(savedPostId).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("게시글 id로 단건 게시물을 찾을 수 있다.")
    void findByIdTest() throws Exception {
        //TODO: 테스트 통과 못함, 수정 예정
        //given

        //when
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(converter.toPostResponseDto(post)).thenReturn(postResponseDto);
        var responseDto = postService.findById(post.getId());

        //then
        assertThat(post.getContent()).isEqualTo(responseDto.getContent());
        assertThat(post.getTitle()).isEqualTo(responseDto.getTitle());
        assertThat(post.getId()).isEqualTo(responseDto.getPostId());
    }

    @Test
    @DisplayName("페이징 처리를 기반으로 전체 게시물을 조회할 수 있다.")
    void findAllByPagesTest() {
        //TODO: 테스트 통과 못함, 수정 예정
        //given
        PageRequest page = PageRequest.of(0, 10);

        //when
        Page<PostResponseDto> all = postService.findAllByPages(page);

        //then
        assertThat(all.getTotalElements()).isEqualTo(1);  //setUp에서 한개를 넣었으므로 1임.
    }

}