package com.prgms.springbootboardjpa.service;

import com.prgms.springbootboardjpa.dto.CreateMemberRequest;
import com.prgms.springbootboardjpa.dto.DtoMapper;
import com.prgms.springbootboardjpa.dto.MemberDto;
import com.prgms.springbootboardjpa.exception.MemberNotFoundException;
import com.prgms.springbootboardjpa.model.Member;
import com.prgms.springbootboardjpa.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 성공")
    void signup_test_success() {
        // given
        Long memberId = 1L;
        String name = "name";
        int age = 111;
        String hobby = "hobby";

        CreateMemberRequest memberDto = new CreateMemberRequest(name, age, hobby);
        Member member = new Member(name, age, hobby);
        Member savedMember = new Member(memberId, name, age, hobby);

        // when
        when(memberRepository.save(any(Member.class)))
                .thenReturn(savedMember);

        // then
        Long savedMemberId = memberService.signupMember(memberDto);

        assertThat(memberId, is(savedMemberId));
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("member 찾기 성공")
    void getUserById_success() {
        Member member = new Member(1L, "name", 10, "hobby");
        MemberDto memberDto = DtoMapper.memberToMemberDto(member);

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));
        MemberDto foundMemberDto = memberService.getMemberById(1L);

        verify(memberRepository, times(1)).findById(1L);
        assertThat(memberDto, samePropertyValuesAs(foundMemberDto));
    }

    @Test
    @DisplayName("member 찾기 실패")
    void getUserById_fail() {
        when(memberRepository.findById(999L))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(MemberNotFoundException.class, () -> {
            memberService.getMemberById(999L);
        });
    }
}