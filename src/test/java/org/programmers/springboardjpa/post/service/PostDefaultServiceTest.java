package org.programmers.springboardjpa.post.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.programmers.springboardjpa.domain.post.domain.Post;
import org.programmers.springboardjpa.domain.post.dto.PostRequest;
import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostCreateRequest;
import org.programmers.springboardjpa.domain.post.dto.PostResponse.PostResponseDto;
import org.programmers.springboardjpa.domain.post.repository.PostRepository;
import org.programmers.springboardjpa.domain.post.service.PostConverter;
import org.programmers.springboardjpa.domain.post.service.PostDefaultService;
import org.programmers.springboardjpa.domain.user.domain.User;
import org.programmers.springboardjpa.domain.user.dto.UserDto;
import org.programmers.springboardjpa.domain.user.dto.UserDto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PostDefaultServiceTest {

    @InjectMocks
    private PostDefaultService postDefaultService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostConverter postConverter;

    private Post entity;
    private PostResponseDto response;

    @BeforeEach
    void setUp() {
        entity = Post.builder()
                .id(1L)
                .title("가입인사")
                .content("냉무")
                .user(User.builder()
                        .id(1L)
                        .name("익명")
                        .age(14)
                        .hobby("LOL")
                        .build())
                .build();
        response = new PostResponseDto(1L, "안녕하세요", "반가워요",
                new UserResponse(1L, "익명", 14, "LOL"));
    }

    @Test
    @DisplayName("save 행위 검증 테스트")
    void savePost() {
        Post post = Post.builder()
                .title("안녕하세요")
                .content("반가워요")
                .user(User.builder()
                        .name("익명")
                        .age(14)
                        .hobby("LOL")
                        .build())
                .build();
        PostCreateRequest createRequest = new PostCreateRequest("안녕하세요", "반가워요",
                new UserDto.UserRequest("익명", 14, "LOL"));
        given(postConverter.toPost(createRequest)).willReturn(post);
        given(postRepository.save(post)).willReturn(entity);

        postDefaultService.savePost(createRequest);

        then(postRepository).should(times(1)).save(post);
        then(postConverter).should(times(1)).toPost(createRequest);
    }

    @Test
    @DisplayName("findById 행위 검증 테스트")
    void findById() {
        given(postRepository.findById(1L)).willReturn(Optional.ofNullable(entity));
        given(postConverter.toPostDto(entity)).willReturn(response);

        postDefaultService.getPost(1L);

        then(postRepository).should(times(1)).findById(1L);
        then(postConverter).should(times(1)).toPostDto(entity);
    }

    @Test
    @DisplayName("findAll 행위 검증 테스트")
    void findAll() {
        var pageable = Pageable.ofSize(1).withPage(1);
        List<Post> postList = List.of(entity);
        Page<Post> postPage = new PageImpl<>(postList);
        given(postRepository.findAll(pageable)).willReturn(postPage);

        postRepository.findAll(pageable);

        then(postRepository).should(times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("update 행위 검증 테스트")
    void update() {
        var updateRequest = new PostRequest.PostUpdateRequest("안녕하세요", "반가워요");
        given(postRepository.findById(1L)).willReturn(Optional.ofNullable(entity));
        given(postConverter.toPostDto(any(Post.class))).willReturn(response);

        postDefaultService.updatePost(1L, updateRequest);

        then(postRepository).should(times(1)).findById(1L);
        then(postConverter).should(times(1)).toPostDto(entity);
    }
}