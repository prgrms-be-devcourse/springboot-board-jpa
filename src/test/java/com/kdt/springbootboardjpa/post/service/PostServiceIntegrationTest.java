package com.kdt.springbootboardjpa.post.service;

import com.kdt.springbootboardjpa.global.exception.NotFoundEntityException;
import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.member.repository.MemberRepository;
import com.kdt.springbootboardjpa.post.domain.Post;
import com.kdt.springbootboardjpa.post.repository.PostRepository;
import com.kdt.springbootboardjpa.post.service.dto.CreatePostRequest;
import com.kdt.springbootboardjpa.post.service.dto.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static com.kdt.springbootboardjpa.global.exception.ExceptionMessage.POST_NOT_EXIST;
import static com.kdt.springbootboardjpa.member.domain.Hobby.GAME;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@Transactional
@SpringBootTest
class PostServiceIntegrationTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;

    Long memberId;
    Long postId;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .name("최은비")
                .age(24)
                .hobby(GAME)
                .build();

        memberId = memberRepository.save(member).getId();

        Post post = Post.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .member(member)
                .build();

        postId = postRepository.save(post).getId();
    }

    @Test
    @DisplayName("게시판 생성 - 성공")
    void createPost() {
        // given
        CreatePostRequest postRequest = CreatePostRequest.builder()
                .title("첫번째 게시판")
                .content("첫번째 게시판 내용")
                .memberId(memberId)
                .build();

        log.info("postRequest : {}", postRequest.getTitle());
        log.info("postRequest : {}", postRequest.getContent());
        log.info("postRequest : {}", postRequest.getMemberId());

        // when
        PostResponse postResponse = postService.save(postRequest);
        log.info(postResponse.getTitle());
        log.info(postResponse.getContent());
        log.info(postResponse.getMemberName());

        Post post = postRepository.findById(postResponse.getId())
                .orElseThrow(() -> new NotFoundEntityException(POST_NOT_EXIST));

        // then
        // 아이디 검증은 필수
        assertThat(postResponse.getId()).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("게시판 전체 조회 - 성공")
    void findAll() {
        PageRequest page = PageRequest.of(0, 10);

        Page<PostResponse> all = postService.findAll(page);

        assertThat(all).hasSize(1);
    }

    @Test
    @DisplayName("게시판 단건 조회 - 성공")
    void findOne() {
        PostResponse expect = postService.findById(postId);

        Post actual = postRepository.findById(expect.getId())
                .orElseThrow(() -> new NotFoundEntityException(POST_NOT_EXIST));

        assertThat(actual.getId()).isEqualTo(expect.getId());
    }
}