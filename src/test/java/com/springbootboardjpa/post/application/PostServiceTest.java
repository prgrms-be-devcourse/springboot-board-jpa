package com.springbootboardjpa.post.application;

import com.springbootboardjpa.global.TestConfig;
import com.springbootboardjpa.member.domain.Member;
import com.springbootboardjpa.member.domain.MemberRepository;
import com.springbootboardjpa.member.domain.Name;
import com.springbootboardjpa.post.domain.Content;
import com.springbootboardjpa.post.domain.Post;
import com.springbootboardjpa.post.domain.PostRepository;
import com.springbootboardjpa.post.dto.PostRequest;
import com.springbootboardjpa.post.dto.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    private Post post;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(new Name("근우", "이"), 25, "독서");
        Content content = new Content("안녕하세요");
        post = new Post(member, content, "자기소개");
    }

    @Test
    void 게시판을_저장한다() {
        // given
        PostRequest request = new PostRequest(1L, "안녕하세요", "자기소개");

        given(postRepository.save(any())).willReturn(post);
        given(memberRepository.getById(any())).willReturn(member);

        // when
        PostResponse result = postService.save(request);

        // then
        assertThat(result.title()).isEqualTo(request.title());
    }
}
