package com.prgrms.boardjpa.domain.post.service;

import com.prgrms.boardjpa.domain.member.Hobby;
import com.prgrms.boardjpa.domain.member.Member;
import com.prgrms.boardjpa.domain.member.repository.MemberJPARepository;
import com.prgrms.boardjpa.domain.post.dto.PostCreateRequestDto;
import com.prgrms.boardjpa.domain.post.dto.PostResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    MemberJPARepository memberJPARepository;

    @Test
    void 글_등록() {
        Member member = memberJPARepository.save(Member.builder()
                .age(12)
                .hobby(Hobby.ACTIVE)
                .name("kim")
                .build());


        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto(member.getId(), "테스트 제목", "테스트 내용");
        PostResponseDto postResponseDto = postService.createPost(postCreateRequestDto);
        assertThat(postResponseDto.getMember()).isEqualTo(member);
    }

}