package com.prgrms.boardjpa.domain.post.repository;

import com.prgrms.boardjpa.domain.member.Hobby;
import com.prgrms.boardjpa.domain.member.Member;
import com.prgrms.boardjpa.domain.member.repository.MemberJPARepository;
import com.prgrms.boardjpa.domain.post.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostJPARepositoryTest {

    @Autowired
    private PostJPARepository postJPARepository;
    @Autowired
    private MemberJPARepository memberJPARepository;

    long id;

    @BeforeAll
    void setUp() {
        Member member = Member.builder()
                .name("kim")
                .age(12)
                .hobby(Hobby.ACTIVE)
                .build();
        Member savedMember = memberJPARepository.save(member);
        id = savedMember.getId();

        Post post = Post.builder()
                .title("테스트 제목")
                .content("테스트 글")
                .member(savedMember)
                .build();
        post.enrollWriter(savedMember);
        postJPARepository.save(post);
    }

    @Test
    void 게시글_추가() {
        Member member = memberJPARepository.findById(id).get();
        Post post = Post.builder()
                .title("테스트 제목2")
                .content("테스트 글2")
                .member(member)
                .build();
        post.enrollWriter(member);
        postJPARepository.save(post);

        assertThat(member.getPosts().size()).isEqualTo(2);
        assertThat(postJPARepository.count()).isEqualTo(2);
    }

}