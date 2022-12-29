package com.spring.board.springboard.post.service;

import com.spring.board.springboard.post.domain.dto.RequestPostDto;
import com.spring.board.springboard.post.domain.dto.ResponsePostDto;
import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
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
    void setUp(){
        Member member = new Member("이수린", 24, Hobby.SLEEP);
        memberRepository.save(member);

        RequestPostDto requestPostDTO1 = new RequestPostDto(
                "스프링 게시판 미션",
                "이 미션 끝나면 크리스마스에요",
                1);
        RequestPostDto requestPostDTO2 = new RequestPostDto(
                "데브코스",
                "프로그래머스 데브코스 완전 좋아요",
                1);
        RequestPostDto requestPostDTO3 = new RequestPostDto(
                "하기싫어",
                "자고싶다",
                1);

        postService.createPost(requestPostDTO1);
        postService.createPost(requestPostDTO2);
        postService.createPost(requestPostDTO3);
    }

    @Test
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
    void createPost() {
        // given
        RequestPostDto requestPostDTO = new RequestPostDto(
                "새로운 게시글입니다.",
                "새로운 게시글입니다. 매번 뭘 써야할 지 고민이네요",
                1
        );

        // when
        ResponsePostDto createdPostDTO = postService.createPost(requestPostDTO);

        // then
        ResponsePostDto findPostDTO = postService.getOne(
                createdPostDTO.postId());
        assertThat(findPostDTO)
                .usingRecursiveComparison()
                .isEqualTo(createdPostDTO);
    }

    @Test
    void update() {
        // given
        String changeTitle = "수정제목";
        String changeContent = "수정 내용입니다. 쿠쿠쿠쿠쿠";

        RequestPostDto beforeUpdatePostDTO = new RequestPostDto(
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