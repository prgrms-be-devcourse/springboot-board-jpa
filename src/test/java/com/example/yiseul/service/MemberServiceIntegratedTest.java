package com.example.yiseul.service;

import com.example.yiseul.domain.Member;
import com.example.yiseul.dto.member.MemberCreateRequestDto;
import com.example.yiseul.dto.member.MemberResponseDto;
import com.example.yiseul.dto.member.MemberUpdateRequestDto;
import com.example.yiseul.global.exception.BaseException;
import com.example.yiseul.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DataJpaTest
class MemberServiceIntegratedTest {

    @Autowired
    MemberRepository memberRepository;

    MemberService memberService;
    MemberCreateRequestDto createRequestDto;
    MemberResponseDto savedMemberDto;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
        createRequestDto = new MemberCreateRequestDto("hihi", 22, "basketball");
        savedMemberDto = memberService.createMember(createRequestDto);
    }

    @Test
    @DisplayName("멤버 저장에 성공한다.")
    void createMember() {
        // then
        memberService.createMember(createRequestDto);
        assertThat(savedMemberDto).isNotNull();
    }

    @Test
    @DisplayName("멤버 조회에 성공한다.")
    void getMember() {
        // when
        MemberResponseDto memberResponseDto = memberService.getMember(savedMemberDto.memberId());

        // then
        assertThat(memberResponseDto.name()).isEqualTo("hihi");
        assertThat(memberResponseDto.age()).isEqualTo(22);
        assertThat(memberResponseDto.hobby()).isEqualTo("basketball");
    }

    @Test
    @DisplayName("멤버 수정에 성공한다.")
    void updateMember() {
        // given
        MemberUpdateRequestDto updateDto = new MemberUpdateRequestDto("hihi2", 25, "baseball");

        // when
        memberService.updateMember(savedMemberDto.memberId(), updateDto);

        // then
        MemberResponseDto member = memberService.getMember(savedMemberDto.memberId());
        assertThat(member.name()).isEqualTo(updateDto.name());
        assertThat(member.age()).isEqualTo(updateDto.age());
        assertThat(member.hobby()).isEqualTo(updateDto.hobby());
    }

    @Test
    @DisplayName("멤버 삭제에 성공한다.")
    void deleteMember() {
        memberService.deleteMember(savedMemberDto.memberId());

        assertThatThrownBy(() -> memberService.getMember(savedMemberDto.memberId()))
                .isInstanceOf(BaseException.class);
    }
}

