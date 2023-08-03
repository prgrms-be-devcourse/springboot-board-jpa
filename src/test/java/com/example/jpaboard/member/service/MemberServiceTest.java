package com.example.jpaboard.member.service;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.service.dto.FindMemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;
    Long id;

    @BeforeEach
    void setUp() {
        Member member = new Member("김별", new Age(26), "산책");
        memberRepository.save(member);
        id = member.getId();
    }

    @Test
    void findById_Member_Equals() {
        //when
        FindMemberResponse response = memberService.findById(id);

        //then
        assertThat(response.getName()).isEqualTo("김별");
        assertThat(response.getHobby()).isEqualTo("산책");
        assertThat(response.getAge()).usingRecursiveComparison().isEqualTo(new Age(26));
    }

}
