package com.kdt.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.kdt.domain.post.Post;
import com.kdt.domain.post.PostRepository;
import com.kdt.post.dto.PostDto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    PostConvertor postConvertor;

    @Mock
    PostDto postDto;

    @Mock
    Post post;

    @Test
    @DisplayName("postDto를 post로 변환하여 저장한다.")
    void savePostDtoToPost() {
        //given
        given(postConvertor.convertPostDtoToPost(postDto)).willReturn(post);
        given(postRepository.save(post)).willReturn(post);
        given(post.getId()).willReturn(1L);

        //when
        Long savePostId = postService.save(postDto);

        //then
        then(postRepository).should().save(post);
        then(postConvertor).should().convertPostDtoToPost(postDto);
        assertThat(savePostId).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시물을 페이징 요청한다.")
    void getPagingPosts() {
        //given
        List<Post> posts = LongStream.range(0, 30)
                .mapToObj(i -> Post.builder().id(i).build())
                .collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(0, 10);
        given(postRepository.findAll(pageRequest)).willReturn(new PageImpl<>(posts));

        //when
        Page<PostDto> findAll = postService.findAll(pageRequest);

        //then
        assertThat(findAll.getTotalElements()).isEqualTo(30);
    }

    @Test
    @DisplayName("게시물을 Dto로 변환하여 조회한다.")
    void getPost() {
        //given
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(postConvertor.convertPostToPostDto(post)).willReturn(postDto);

        //when
        PostDto findPostDto = postService.findOne(1L);

        //then
        then(postRepository).should().findById(1L);
        then(postConvertor).should().convertPostToPostDto(post);
        assertThat(findPostDto).isNotNull();
    }

    @Test
    @DisplayName("postDto를 post로 변환하여 수정한다.")
    void updatePostDtoToPost() {
        //given
        given(postDto.getId()).willReturn(1L);
        given(postRepository.findById(postDto.getId())).willReturn(Optional.of(post));
        given(postConvertor.convertPostDtoToPost(postDto)).willReturn(post);
        given(post.getId()).willReturn(1L);

        //when
        Long savePostId = postService.update(postDto);

        //then
        then(postRepository).should().findById(1L);
        assertThat(savePostId).isEqualTo(1L);
    }

}
