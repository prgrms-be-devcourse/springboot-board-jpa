package com.example.yiseul.service;


import com.example.yiseul.converter.PostConverter;
import com.example.yiseul.domain.Member;
import com.example.yiseul.domain.Post;
import com.example.yiseul.dto.post.PostCreateRequestDto;
import com.example.yiseul.dto.post.PostResponseDto;
import com.example.yiseul.dto.post.PostUpdateRequestDto;
import com.example.yiseul.global.exception.PostException;
import com.example.yiseul.repository.MemberRepository;
import com.example.yiseul.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PostService postService;

//    private PostConverter postConverter; // 질문

    private Member member1;
    private Post post;

    @BeforeEach
    void setUp(){
        member1 = new Member("hihi", 22, "basketball");
        post = Post.createPost(member1, "jaws", "scary");
    }

    @Test
    @DisplayName("게시물을 생성할 수 있다.")
    void postCreateSuccess(){
        //given
        PostCreateRequestDto createRequestDto = new PostCreateRequestDto(1L, "jaws", "swim");

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(member1));

        Post post = PostConverter.convertPost(member1, createRequestDto);

        given(postRepository.save(any(Post.class)))
                .willReturn(post);

        //when
        PostResponseDto postResponseDto = postService.createPost(createRequestDto);

        // then
        assertThat(postResponseDto).isNotNull();
    }

    @Test
    @DisplayName("게시물 조회에 성공한다.")
    void getPostSuccess() {
        // given
        Long id = 1L;

        given(postRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(post));

        // when
        PostResponseDto responseDto = postService.getPost(id);

        // then
        assertThat(responseDto.title()).isEqualTo(post.getTitle());
        assertThat(responseDto.content()).isEqualTo(post.getContent());
        assertThat(post.getMember().getName()).isEqualTo("hihi");
    }

    @Test
    @DisplayName("게시글 조회에 실패한다.")
    void getMemberFail() {
        // given
        Long id = 2L;

        given(postRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.getPost(id))
                .isInstanceOf(PostException.class);
    }

    @Test
    @DisplayName("게시글 수정에 성공한다.")
    void updatePost() {
        // given
        Long id = 1L;

        PostUpdateRequestDto updateRequestDto = new PostUpdateRequestDto("jaws", "swim");

        given(postRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(post));

        // when
        postService.updatePost(id, updateRequestDto);

        // then
        assertThat(post.getTitle()).isEqualTo(updateRequestDto.title());
        assertThat(post.getContent()).isEqualTo(updateRequestDto.content());
    }

    @Test
    @DisplayName("게시글 삭제에 성공한다.") // 질문 : 실패하는 요인..?
    void deletePost() {
        // given
        Long id = 1L;

        given(postRepository.existsById(anyLong())).willReturn(true);

        doNothing().when(postRepository).deleteById(anyLong());

        // when
        postService.deletePost(id);

        // verify
        verify(postRepository).deleteById(anyLong());
    }
}
