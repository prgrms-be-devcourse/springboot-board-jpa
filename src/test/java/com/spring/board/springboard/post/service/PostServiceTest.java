package com.spring.board.springboard.post.service;

import com.spring.board.springboard.post.domain.dto.PostCreateRequestDto;
import com.spring.board.springboard.post.domain.dto.ResponsePostDto;
import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeAll
    void setUp() {
        Member member = new Member("user@naver.com", "password1234", "이수린", 24, Hobby.SLEEP);
        memberRepository.save(member);

        PostCreateRequestDto postCreateRequestDTO1 = new PostCreateRequestDto(
                "스프링 게시판 미션",
                "이 미션 끝나면 크리스마스에요",
                1);
        PostCreateRequestDto postCreateRequestDTO2 = new PostCreateRequestDto(
                "데브코스",
                "프로그래머스 데브코스 완전 좋아요",
                1);
        PostCreateRequestDto postCreateRequestDTO3 = new PostCreateRequestDto(
                "하기싫어",
                "자고싶다",
                1);

        postService.createPost(postCreateRequestDTO1);
        postService.createPost(postCreateRequestDTO2);
        postService.createPost(postCreateRequestDTO3);
    }

    @Test
    @DisplayName("모든 게시물을 페이지 단위로 가져올 수 있다.")
    void getAll() {
        // given
        int size = 2;
        PageRequest page = PageRequest.of(0, size);

        // when
        List<ResponsePostDto> postList = postService.getAll(page);

        // then
        assertThat(postList.size())
                .isEqualTo(size);
    }

    @Test
    @DisplayName("하나의 게시물을 조회할 수 있다.")
    void getOne() {
        // given
        int id = 1;

        // when
        ResponsePostDto findPostDTO = postService.getOne(id);

        // then
        assertThat(findPostDTO.postId())
                .isEqualTo(id);
    }

    @Test
    @DisplayName("새로운 게시글을 등록할 수 있다.")
    void createPost() {
        // given
        PostCreateRequestDto postCreateRequestDTO = new PostCreateRequestDto(
                "새로운 게시글입니다.",
                "새로운 게시글입니다. 매번 뭘 써야할 지 고민이네요",
                1
        );

        // when
        ResponsePostDto createdPostDTO = postService.createPost(postCreateRequestDTO);

        // then
        ResponsePostDto findPostDTO = postService.getOne(
                createdPostDTO.postId());
        assertThat(findPostDTO)
                .usingRecursiveComparison()
                .isEqualTo(createdPostDTO);
    }

    @Test
    @DisplayName("게시물의 제목과 내용을 수정할 수 있다.")
    void update() {
        // given
        String changeTitle = "수정제목";
        String changeContent = "수정 내용입니다. 쿠쿠쿠쿠쿠";

        PostCreateRequestDto beforeUpdatePostDTO = new PostCreateRequestDto(
                changeTitle,
                changeContent,
                1
        );

        // when
        int postId = 3;
        ResponsePostDto updatedPostDto = postService.update(postId, beforeUpdatePostDTO);

        // then
        assertThat(postId)
                .isEqualTo(
                        updatedPostDto.postId());

        assertThat(updatedPostDto)
                .usingRecursiveComparison()
                .isNotEqualTo(beforeUpdatePostDTO);
    }
}