package com.kdt.springbootboardjpa.post.service;

import com.kdt.springbootboardjpa.member.domain.Hobby;
import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.post.domain.Post;
import com.kdt.springbootboardjpa.post.repository.PostRepository;
import com.kdt.springbootboardjpa.post.service.converter.PostConverter;
import com.kdt.springbootboardjpa.post.service.dto.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceUnitTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostConverter postConverter;

//    @Test
//    @DisplayName("postService createPost")
//    void savePost() {
//        //ReflectionTestUtils.setField(createMember(), "memberId", createMember().getId());
//    }

    @Test
    @DisplayName("게시판 findAll - 성공")
    void findAll() {
        // given
        Post post = createdPost();
        PostResponse postResponse = createdPostResponse();

        PageRequest page = PageRequest.of(0, 10);

        given(postRepository.findAll(page)).willReturn(postAll(List.of(post)));
        given(postConverter.postToResponse(post)).willReturn(postResponse);

        // when
        Page<PostResponse> all = postService.findAll(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(1);
        //assertThat(all.getContent().get(0).getContent()).isEqualTo(postResponse.getContent());
    }

    public Page<Post> postAll(List<Post> post) {
        return new PageImpl<>(post);
    }

    public PostResponse createdPostResponse() {
        return PostResponse.builder()
                .id(1L)
                .title("테스트 제목")
                .content("테스트 내용")
                .memberName("최은비")
                .build();
    }

    public Post createdPost() {
        return Post.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .member(createdMember())
                .build();
    }

    public Member createdMember() {
        return Member.builder()
                .name("최은비")
                .age(25)
                .hobby(Hobby.GAME)
                .build();
    }
}