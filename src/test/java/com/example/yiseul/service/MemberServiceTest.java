package com.example.yiseul.service;

import com.example.yiseul.domain.Member;
import com.example.yiseul.dto.member.MemberCreateRequestDto;
import com.example.yiseul.dto.member.MemberResponseDto;
import com.example.yiseul.dto.member.MemberUpdateRequestDto;
import com.example.yiseul.global.exception.MemberException;
import com.example.yiseul.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("hihi", 22, "basketball");
    }

    @Test
    @DisplayName("멤버 저장에 성공한다.")
    void createMember() {
        // given
        MemberCreateRequestDto createRequestDto = new MemberCreateRequestDto(member.getName(), member.getAge(), member.getHobby());

        given(memberRepository.save(any(Member.class)))
                .willReturn(member);

        // when
        MemberResponseDto responseDto = memberService.createMember(createRequestDto);

        // then
        assertThat(responseDto).isNotNull();
    }

    @Test
    @DisplayName("멤버 조회에 성공한다.")
    void getMemberSuccess() {
        // given
        Long id = 1L;

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(member));

        // when
        MemberResponseDto responseDto = memberService.getMember(id);

        // then
        assertThat(responseDto.name()).isEqualTo(member.getName());
        assertThat(responseDto.age()).isEqualTo(member.getAge());
        assertThat(responseDto.hobby()).isEqualTo(member.getHobby());
    }

    @Test
    @DisplayName("멤버 조회에 실패한다.")
    void getMemberFail() {
        // given
        Long id = 2L;

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberService.getMember(id))
                .isInstanceOf(MemberException.class);
    }

    @Test
    @DisplayName("멤버 수정에 성공한다.")
    void updateMember() {
        // given
        Long id = 1L;

        MemberUpdateRequestDto updateRequestDto = new MemberUpdateRequestDto("hihi2",23 , "swim");

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(member));

        // when
        memberService.updateMember(id, updateRequestDto);

        // then
        assertThat(member.getName()).isEqualTo(updateRequestDto.name());
        assertThat(member.getAge()).isEqualTo(updateRequestDto.age());
        assertThat(member.getHobby()).isEqualTo(updateRequestDto.hobby());
    }

    @Test
    @DisplayName("멤버 삭제에 성공한다.")
    void deleteMember() {
        // given
        given(memberRepository.existsById(anyLong()))
                .willReturn(true);

        // when & then
        assertThatCode(() -> memberService.deleteMember(anyLong()))
                .doesNotThrowAnyException();

        // verify
        verify(memberRepository).deleteById(anyLong());
    }
}