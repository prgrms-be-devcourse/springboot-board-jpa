package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberDetailResponse;
import com.example.board.domain.member.dto.MemberUpdateRequest;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.global.exception.BusinessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private MemberCreateRequest request = new MemberCreateRequest("test1@gmail.com", "홍길동", "test1234!",22, "농구");;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    void 멤버_생성_테스트() {
        // Given

        // When
        MemberDetailResponse savedMember = memberService.createMember(request);

        // Then
        assertThat(savedMember.email()).isEqualTo(request.email());
        assertThat(savedMember.name()).isEqualTo(request.name());
        assertThat(savedMember.age()).isEqualTo(request.age());
        assertThat(savedMember.hobby()).isEqualTo(request.hobby());
    }

    @Test
    void 이메일_중복_테스트() {
        // Given
        memberService.createMember(request);

        // When & Then
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(BusinessException.class);
    }
    
    @Test
    void 회원_아이디_조회_테스트() {
        // Given
        MemberDetailResponse savedMember = memberService.createMember(request);

        // When
        MemberDetailResponse findMember = memberService.findMemberById(savedMember.id());

        // Then
        assertThat(findMember.email()).isEqualTo(savedMember.email());
        assertThat(findMember.name()).isEqualTo(savedMember.name());
        assertThat(findMember.age()).isEqualTo(savedMember.age());
        assertThat(findMember.hobby()).isEqualTo(savedMember.hobby());
    }

    @Test
    void 회원_아이디_조회_실패_테스트() {
        // Given
        Long notExistId = 2L;

        // When & Then
        assertThatThrownBy(() -> memberService.findMemberById(notExistId))
            .isInstanceOf(BusinessException.class);
    }

    @Test
    void 회원_전체조회_테스트() {
        // Given
        memberService.createMember(request);

        // When
        List<MemberDetailResponse> findMembers = memberService.findAllMembers();
        
        // Then
        assertThat(findMembers).hasSize(1);
        assertThat(findMembers.get(0).email()).isEqualTo(request.email());
    }
    
    @Test
    void 회원_수정_테스트() {
        // Given
        MemberDetailResponse originMember = memberService.createMember(request);
        MemberUpdateRequest request = new MemberUpdateRequest("길동이", "수영");

        // When
        MemberDetailResponse updatedMember = memberService.updateMember(originMember.id(), request);

        // Then
        assertThat(updatedMember.name()).isEqualTo(request.name());
        assertThat(updatedMember.hobby()).isEqualTo(request.hobby());
    }

    @Test
    void 회원_아이디로_삭제_테스트() {
        // Given
        MemberDetailResponse savedMember = memberService.createMember(request);

        // When
        memberService.deleteMemberById(savedMember.id());

        // Then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(0);
    }

    @Test
    void 회원_전체_삭제_테스트() {
        // Given
        memberService.createMember(request);

        // When
        memberService.deleteAllMembers();

        // Then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(0);
    }
}
