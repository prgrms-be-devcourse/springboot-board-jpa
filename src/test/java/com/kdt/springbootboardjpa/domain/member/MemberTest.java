package com.kdt.springbootboardjpa.domain.member;

import com.kdt.springbootboardjpa.repository.member.MemberJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.kdt.springbootboardjpa.domain.member.Hobby.GAME;
import static com.kdt.springbootboardjpa.domain.member.Hobby.MOVIE;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MemberTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    Member basicMember;

    @BeforeAll
    void setData() {
        basicMember = Member.builder()
                .name("테스트 기본 데이터 이름")
                .age(10)
                .hobby(MOVIE)
                .build();

        memberJpaRepository.save(basicMember);
    }

    @Test
    @DisplayName("회원(Member) 정보를 전체 조회한다.")
    void member_select() {
        List<Member> members = memberJpaRepository.findAll();

//        assertThat(members).isNotEmpty();
        assertThat(members).hasSize(1);
        log.info("조회한 member 개수 : {}", members.size());
    }

    @Test
    @DisplayName("새로운 회원(Member) 정보를 저장한다.")
    void insertMember() {

        Member newMember = Member.builder()
                .name("최은비")
                .age(24)
                .hobby(GAME)
                .build();

        Member saveMember = memberJpaRepository.save(newMember);

        assertThat(saveMember)
                .hasFieldOrPropertyWithValue("id", newMember.getId())
                .hasFieldOrPropertyWithValue("name", newMember.getName())
                .hasFieldOrPropertyWithValue("age", newMember.getAge())
                .hasFieldOrPropertyWithValue("hobby", newMember.getHobby());
    }

    @Test
    @DisplayName("기존 회원(Member) 이름을 수정한다.")
    void updateMember() {
        String updateName = "수정된 테스트 이름";
        memberJpaRepository.findById(basicMember.getId()).ifPresent(findByIdMember -> findByIdMember.changeName(updateName));

        Optional<Member> updatedMember = memberJpaRepository.findById(basicMember.getId());

        assertThat(updatedMember)
                .hasValueSatisfying(member -> assertThat(member.getName()).isEqualTo(updateName));
    }

    @Test
    @DisplayName("기존 회원(Member)를 삭제한다.")
    void deleteMember() {
        memberJpaRepository.delete(basicMember);

        Optional<Member> findMember = memberJpaRepository.findById(basicMember.getId());

        assertThat(findMember).isEmpty();
    }
}