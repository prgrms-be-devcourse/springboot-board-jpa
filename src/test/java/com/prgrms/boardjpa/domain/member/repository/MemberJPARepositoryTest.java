package com.prgrms.boardjpa.domain.member.repository;

import com.prgrms.boardjpa.domain.member.Hobby;
import com.prgrms.boardjpa.domain.member.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberJPARepositoryTest {

    @Autowired
    MemberJPARepository memberJPARepository;

    @BeforeAll
    void 데이터베이스_초기화() {
        Member member = Member.builder()
                .age(12)
                .hobby(Hobby.ACTIVE)
                .name("kim").build();
       memberJPARepository.save(member);
    }

    @Test
    void 멤버_추가() {
        Member member = Member.builder()
                .age(12)
                .hobby(Hobby.ACTIVE)
                .name("kim").build();
        Member savedMember = memberJPARepository.save(member);
        assertThat(savedMember.getName()).isEqualTo("kim");
        assertThat(savedMember.getAge()).isEqualTo(12);
        assertThat(savedMember.getHobby()).isEqualTo(Hobby.ACTIVE);
    }

    @Test
    void 멤버_수정() {
        Member findMember = memberJPARepository.findById(1L).get();
        findMember.changeName("jung");
        Member changedMember = memberJPARepository.findById(1L).get();
        assertThat(changedMember.getName()).isEqualTo("jung");
    }

    @Test
    void 멤버_전체_조회() {
        List<Member> all = memberJPARepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }
}