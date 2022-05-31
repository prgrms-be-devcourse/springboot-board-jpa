package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.dto.UserResponseDto;
import com.example.board.repository.PostRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Post post;

    @Mock
    private PostConverter postConverter;

    private final Long postId = 1L;
    private final Long userId = 2L;

    @Test
    @DisplayName("게시물을 저장한다")
    void saveTest() {
        // given
        PostRequestDto postRequestDto = new PostRequestDto(
                "제목",
                "내용",
                new UserResponseDto(userId, "이름", 25, "취미"));

        given(postConverter.convertPost(postRequestDto)).willReturn(post);
        given(postRepository.save(post)).willReturn(post);

        PostResponseDto postResponseDto = new PostResponseDto(
                postId,
                "제목",
                "내용",
                new UserResponseDto(userId, "이름", 25, "취미"));
        given(postConverter.convertPostResponseDto(post)).willReturn(postResponseDto);

        // when
        PostResponseDto result = postService.writePost(postRequestDto);

        // then
        then(postRepository).should().save(post);
        assertThat(result.id()).isEqualTo(postId);
    }

    @Test
    @DisplayName("하나의 게시물을 반환한다")
    void getOnePostTest() throws NotFoundException {
        // given
        PostResponseDto response = new PostResponseDto(
                postId,
                "제목",
                "내용",
                new UserResponseDto(userId, "이름", 25, "취미"));
        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        given(postConverter.convertPostResponseDto(post)).willReturn(response);

        // when
        PostResponseDto postResponseDto = postService.getOnePost(postId);

        // then
        then(postRepository).should().findById(postId);
        assertThat(postResponseDto.id()).isEqualTo(postId);
    }

    @Test
    @DisplayName("게시물을 페이지 단위로 조회한다")
    void getAllPostTest() {
        // given
        PageRequest page = PageRequest.of(0, 10);
        List<Post> postList = new ArrayList<>();
        postList.add(post);

        given(postRepository.findAll(page)).willReturn(new PageImpl<>(postList));

        // when
        Page<PostResponseDto> all = postService.getAllPostByPage(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시물을 업데이트 한다")
    void updatePostTest() throws NotFoundException {
        // given
        PostResponseDto response = new PostResponseDto(
                postId,
                "new-Title",
                "new-Content",
                new UserResponseDto(userId, "이름", 25, "취미"));
        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        given(postConverter.convertPostResponseDto(post)).willReturn(response);

        // when
        postService.updatePost(postId, "new-Title", "new-Content");

        // then
        PostResponseDto updated = postService.getOnePost(postId);
        assertThat(updated.title()).isEqualTo("new-Title");
        assertThat(updated.content()).isEqualTo("new-Content");
    }
}