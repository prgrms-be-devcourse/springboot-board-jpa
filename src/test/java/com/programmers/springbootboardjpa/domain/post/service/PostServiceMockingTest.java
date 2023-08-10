package com.programmers.springbootboardjpa.domain.post.service;

import com.programmers.springbootboardjpa.domain.post.domain.Post;
import com.programmers.springbootboardjpa.domain.post.domain.PostRepository;
import com.programmers.springbootboardjpa.domain.post.dto.PostCreateRequestDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostResponseDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostUpdateRequestDto;
import com.programmers.springbootboardjpa.domain.user.domain.User;
import com.programmers.springbootboardjpa.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PostServiceMockingTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private PostCreateRequestDto postCreateRequestDto;
    private PostUpdateRequestDto postUpdateRequestDto;

    private Post post;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("김이름", 27, "캠핑");
        post = new Post("등록용 제목", "등록용 내용", user);

        postCreateRequestDto = PostCreateRequestDto.builder()
                .title("등록용 제목")
                .content("등록용 내용")
                .userId(1L)
                .build();

        postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("수정용 제목")
                .content("수정용 내용")
                .build();
    }

    @DisplayName("게시글을 저장한다")
    @Test
    void create() {
        //given
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postRepository.findById(null)).thenReturn(Optional.of(post));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        //when
        PostResponseDto postResponseDto = postService.create(postCreateRequestDto);
        PostResponseDto result = postService.findById(postResponseDto.id());

        //then
        assertThat(result.title()).isEqualTo(postCreateRequestDto.title());
        assertThat(result.content()).isEqualTo(postCreateRequestDto.content());
    }

    @DisplayName("게시글을 수정한다")
    @Test
    void update() {
        //given
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
        Long requestPostId = 1L;

        //when
        PostResponseDto postResponseDto = postService.update(requestPostId, postUpdateRequestDto);

        //then
        assertThat(postResponseDto.title()).isEqualTo(postUpdateRequestDto.title());
        assertThat(postResponseDto.content()).isEqualTo(postUpdateRequestDto.content());
    }

    @DisplayName("id로 게시글을 단건 조회한다")
    @Test
    void findById() {
        //given
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
        Long requestPostId = 1L;

        //when
        PostResponseDto postResponseDto = postService.findById(requestPostId);

        //then
        assertThat(postResponseDto.title()).isEqualTo(post.getTitle());
        assertThat(postResponseDto.content()).isEqualTo(post.getContent());
        assertThat(postResponseDto.userId()).isEqualTo(post.getUser().getId());
    }

    @DisplayName("저장된 게시글들을 페이징 조회한다")
    @Test
    void findAll() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<Post> posts = List.of(post);
        Page<Post> postPage = new PageImpl<>(posts, pageRequest, posts.size());

        when(postRepository.findAll(any(PageRequest.class))).thenReturn(postPage);


        //when
        Page<PostResponseDto> result = postService.findAll(pageRequest);

        //then
        assertThat(result).hasSize(1);
    }
}
