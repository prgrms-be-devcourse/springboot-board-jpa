package com.springbootboardjpa.post.domain;

import com.springbootboardjpa.global.TestConfig;
import com.springbootboardjpa.member.domain.Member;
import com.springbootboardjpa.member.domain.MemberRepository;
import com.springbootboardjpa.member.domain.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestConfig.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Post post;

    @BeforeEach
    void setUp() {
        Member member = new Member(new Name("근우", "이"), 25, "독서");
        memberRepository.save(member);
        Content content = new Content("안녕하세요");
        post = new Post(member, content, "자기소개");
    }

    @Test
    void 게시판을_저장한다() {
        // given & when
        Post result = postRepository.save(post);

        // then
        assertThat(post).isEqualTo(result);
    }

    @Test
    void 게시판을_아이디로_조회한다() {
        // given
        Post savedPost = postRepository.save(post);

        // when
        Post result = postRepository.getById(savedPost.getId());

        // then
        assertThat(result).isEqualTo(post);
    }

    @Test
    void 게시판을_모두_조회한다() {
        // given
        Member member = new Member(new Name("근우", "이"), 25, "독서");
        memberRepository.save(member);
        Content content = new Content("안녕하세요");
        Post OtherPost = new Post(member, content, "자기소개");

        postRepository.save(post);
        postRepository.save(OtherPost);

        // when
        List<Post> result = postRepository.findAll();

        // then
        assertThat(result).hasSize(2);
    }
}
