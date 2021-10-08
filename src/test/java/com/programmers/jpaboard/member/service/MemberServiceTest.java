package com.programmers.jpaboard.member.service;

import com.programmers.jpaboard.member.domain.Member;
import com.programmers.jpaboard.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버를 저장한다")
    public void saveTest() {
        // Given
        MemberService memberService = new MemberService(memberRepository);

        Member member = Member.builder()
                .age(20)
                .name("taehyun")
                .hobbies(List.of("Table Tennis", "Computer Game"))
                .build();

        when(memberRepository.save(member)).thenReturn(member);


        // When
        Member actual = memberService.saveMember(member);

        // Then
        assertThat(actual).isEqualTo(member);
    }
}