package com.programmers.jpaboard.member.service;

import com.programmers.jpaboard.member.domain.Member;
import com.programmers.jpaboard.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("멤버를 저장한다")
    public void saveTest() {
        // Given
        Member member = Member.builder()
                .age(20)
                .name("taehyun")
                .hobbies(List.of("Table Tennis", "Computer Game"))
                .build();

        // When
        Member actual = memberService.saveMember(member);

        // Then
        assertThat(actual).isEqualTo(member);
    }
}