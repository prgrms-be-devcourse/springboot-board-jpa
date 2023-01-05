package com.kdt.springbootboardjpa.member.service;

import com.kdt.springbootboardjpa.global.exception.NotFoundEntityException;
import com.kdt.springbootboardjpa.member.domain.Hobby;
import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.member.repository.MemberRepository;
import com.kdt.springbootboardjpa.member.service.converter.MemberConverter;
import com.kdt.springbootboardjpa.member.service.dto.MemberRequestDto;
import com.kdt.springbootboardjpa.member.service.dto.MemberResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
@Slf4j
@ExtendWith(MockitoExtension.class)
class MemberServiceUnitTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberConverter memberConverter;

    @Test
    @DisplayName("회원(Member) 등록 - 성공")
    void createdMemberTest() {

        // given
        Long expectedId = 1L;
        MemberRequestDto createMemberRequest = memberRequest();
        Member member = createdMember();

        given(memberConverter.requestToMember(createMemberRequest)).willReturn(member);
        given(memberRepository.save(member)).willReturn(member);
        given(memberConverter.memberToResponse(member)).willReturn(createdMemberResponse());

        // when
        MemberResponseDto actual = memberService.save(createMemberRequest);

        // then
        assertThat(actual.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("회원(Member) 수정 - 성공")
    void updatedMemberTest_success() {
        // given
        Long expectedId = 1L;
        Member member = createdMember();
        MemberRequestDto updatedMember = updatedMemberRequest();

        given(memberRepository.findById(expectedId)).willReturn(Optional.of(member));
        given(memberConverter.memberToResponse(member)).willReturn(updatedMemberResponse());

        // when
        MemberResponseDto actual = memberService.update(expectedId, updatedMember);


        // then
        assertThat(actual.getId()).isEqualTo(expectedId);
        assertThat(actual.getName()).isEqualTo(updatedMember.getName());
        assertThat(actual.getAge()).isEqualTo(updatedMember.getAge());
        assertThat(actual.getHobby()).isEqualTo(updatedMember.getHobby());
    }

    @Test
    @DisplayName("회원(Member) 수정 - NotFoundException 검증")
    void updatedMemberTest_fail() {
        // given
        MemberRequestDto updatedMember = updatedMemberRequest();
        // when then
        Assertions.assertThrows(NotFoundEntityException.class, () -> memberService.update(100L, updatedMember));
    }

    @Test
    @DisplayName("회원(Member) 삭제 - 성공")
    void deletedMember() {
        // given
        Long expectedId = 1L;
        Member member = createdMember();

        given(memberRepository.findById(expectedId)).willReturn(Optional.of(member));

        // when
        memberService.delete(expectedId);

        // then
        then(memberRepository).should().delete(member);
    }

    public MemberResponseDto updatedMemberResponse() {
        return MemberResponseDto.builder()
                .id(1L)
                .name("변경된 이름")
                .age(100)
                .hobby(Hobby.GAME)
                .build();
    }

    public MemberRequestDto updatedMemberRequest() {
        return MemberRequestDto.builder()
                .name("변경된 이름")
                .age(100)
                .hobby(Hobby.GAME)
                .build();
    }

    public MemberResponseDto createdMemberResponse() {
        return MemberResponseDto.builder()
                .id(1L)
                .name("최은비")
                .age(24)
                .hobby(Hobby.MOVIE)
                .build();
    }

    public MemberRequestDto memberRequest() {
        return MemberRequestDto.builder()
                .name("최은비")
                .age(24)
                .hobby(Hobby.MOVIE)
                .build();
    }

    public Member createdMember() {
        return Member.builder()
                .name("최은비")
                .age(24)
                .hobby(Hobby.MOVIE)
                .build();
    }
}