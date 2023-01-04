package com.spring.board.springboard.post.service;

import com.spring.board.springboard.post.domain.dto.PostCreateRequestDto;
import com.spring.board.springboard.post.domain.dto.PostSummaryResponseDto;
import com.spring.board.springboard.post.domain.dto.PostDetailResponseDto;
import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

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

    private static Integer postId;
    private static Member member;

    @BeforeEach
    void setUp(){
        member = new Member("user@naver.com", "password1234", "이수린", 24, Hobby.SLEEP);
        memberRepository.save(member);

        PostCreateRequestDto postCreateRequestDTO1 = new PostCreateRequestDto(
                "스프링 게시판 미션",
                "이 미션 끝나면 크리스마스에요"
        );
        PostCreateRequestDto postCreateRequestDTO2 = new PostCreateRequestDto(
                "데브코스",
                "프로그래머스 데브코스 완전 좋아요"
        );
        PostCreateRequestDto postCreateRequestDTO3 = new PostCreateRequestDto(
                "하기싫어",
                "자고싶다"
        );

        postId = postService.createPost(postCreateRequestDTO1, member.getEmail())
                .postId();
        postService.createPost(postCreateRequestDTO2, member.getEmail());
        postService.createPost(postCreateRequestDTO3, member.getEmail());
    }

    @AfterAll
    void clean(){
        System.out.println(memberRepository.findAll().size());
    }

    @Test
    @DisplayName("모든 게시물을 페이지 단위로 가져올 수 있다.")
    void getAll() {
        // given
        int size = 2;
        PageRequest page = PageRequest.of(0, size);

        // when
        List<PostSummaryResponseDto> postList = postService.getAll(page);

        // then
        assertThat(postList.size())
                .isEqualTo(size);
    }

    @Test
    @DisplayName("하나의 게시물을 조회할 수 있다.")
    void getOne() {

        // when
        PostDetailResponseDto findPostDTO = postService.getOne(postId);

        // then
        assertThat(findPostDTO.postId())
                .isEqualTo(postId);
    }

    @Test
    @DisplayName("새로운 게시글을 등록할 수 있다.")
    void createPost() {
        // given
        PostCreateRequestDto postCreateRequestDTO = new PostCreateRequestDto(
                "새로운 게시글입니다.",
                "새로운 게시글입니다. 매번 뭘 써야할 지 고민이네요"
        );

        // when
        PostDetailResponseDto createdPostDTO = postService.createPost(postCreateRequestDTO, member.getEmail());

        // then
        PostDetailResponseDto findPostDTO = postService.getOne(
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
                changeContent
        );

        // when
        PostDetailResponseDto updatedPostDto = postService.update(
                postId, beforeUpdatePostDTO, member.getEmail());

        // then
        assertThat(postId)
                .isEqualTo(
                        updatedPostDto.postId());

        assertThat(updatedPostDto)
                .usingRecursiveComparison()
                .isNotEqualTo(beforeUpdatePostDTO);
    }
}