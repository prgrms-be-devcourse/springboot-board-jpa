package com.spring.board.springboard.post.service;

import com.spring.board.springboard.post.domain.dto.RequestPostDTO;
import com.spring.board.springboard.post.domain.dto.ResponsePostDTO;
import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp(){
        Member member = new Member("이수린", 24, Hobby.sleep);
        memberRepository.save(member);

        RequestPostDTO requestPostDTO1 = new RequestPostDTO(
                "스프링 게시판 미션",
                "이 미션 끝나면 크리스마스에요",
                1);
        RequestPostDTO requestPostDTO2 = new RequestPostDTO(
                "데브코스",
                "프로그래머스 데브코스 완전 좋아요",
                1);
        RequestPostDTO requestPostDTO3 = new RequestPostDTO(
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
        List<ResponsePostDTO> postList = postService.getAll(page);

        // then
        assertThat(postList.size())
                .isEqualTo(size);
    }

    @Test
    void getOne() {
        // given
        int id = 1;

        // when
        ResponsePostDTO findPostDTO = postService.getOne(id);

        // then
        assertThat(findPostDTO.getPostId())
                .isEqualTo(id);
    }

    @Test
    void createPost() {
        // given
        RequestPostDTO requestPostDTO = new RequestPostDTO(
                "새로운 게시글입니다.",
                "새로운 게시글입니다. 매번 뭘 써야할 지 고민이네요",
                1
        );

        // when
        ResponsePostDTO createdPostDTO = postService.createPost(requestPostDTO);

        // then
        ResponsePostDTO findPostDTO = postService.getOne(
                createdPostDTO.getPostId());
        assertThat(findPostDTO)
                .usingRecursiveComparison()
                .isEqualTo(createdPostDTO);
    }

    @Test
    void update() {
        // given
        String changeTitle = "수정제목";
        String changeContent = "수정 내용입니다. 쿠쿠쿠쿠쿠";

        RequestPostDTO beforeUpdatePostDTO = new RequestPostDTO(
                changeTitle,
                changeContent,
                1
        );

        // when
        int postId = 3;
        ResponsePostDTO updatedPostDTO = postService.update(postId, beforeUpdatePostDTO);

        // then
        assertThat(postId)
                .isEqualTo(
                        updatedPostDTO.getPostId());

        assertThat(updatedPostDTO)
                .usingRecursiveComparison()
                .isNotEqualTo(beforeUpdatePostDTO);
    }
}