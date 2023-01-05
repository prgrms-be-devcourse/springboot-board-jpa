package com.kdt.springbootboardjpa.member.domain;

import com.kdt.springbootboardjpa.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.kdt.springbootboardjpa.member.domain.Hobby.GAME;
import static com.kdt.springbootboardjpa.member.domain.Hobby.MOVIE;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class MemberEntityTest {

    @Autowired
    MemberRepository memberJpaRepository;

    Member member;

    @BeforeEach
    void setData() {
        member = Member.builder()
                .name("테스트 기본 데이터 이름")
                .age(10)
                .hobby(MOVIE)
                .build();

        Long memberId = memberJpaRepository.save(member).getId();
        log.info("member Id : {}", memberId);
    }

    @Test
    @DisplayName("회원(Member) 정보를 전체 조회한다.")
    void selectMember() {
        List<Member> members = memberJpaRepository.findAll();

        assertThat(members).isNotEmpty();
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

//        Member findMember = memberJpaRepository.findById(newMember.getId()).get();
//        newMember와 saveMember, findMember 주소값이 같다. 결국 Id만 추가된 같은 주소를 가진 객체를 반환
//        log.info("newMember : {} ", newMember);
//        log.info("saveMember : {}", saveMember);
//        log.info("findMember : {}", findMember);

        assertThat(saveMember.getId()).isNotNull(); // id 추가되었는지 확인하는 테스트

        assertThat(saveMember)
                .hasFieldOrPropertyWithValue("id", newMember.getId())
                .hasFieldOrPropertyWithValue("name", newMember.getName())
                .hasFieldOrPropertyWithValue("age", newMember.getAge())
                .hasFieldOrPropertyWithValue("hobby", newMember.getHobby());

    }

    @Test
    @DisplayName("기존 회원(Member) 정보를 수정한다.")
    void updateMember() {
        // when
        memberJpaRepository.findById(member.getId())
                .ifPresent(m -> m.changeMember("변경된 이름", 25, GAME));

        Optional<Member> updatedMember = memberJpaRepository.findById(member.getId());

        assertThat(updatedMember)
                .hasValueSatisfying(member -> assertThat(member.getId()).isEqualTo(this.member.getId()))
                .hasValueSatisfying(member -> assertThat(member.getName()).isEqualTo("변경된 이름"))
                .hasValueSatisfying(member -> assertThat(member.getAge()).isEqualTo(25))
                .hasValueSatisfying(member -> assertThat(member.getHobby()).isEqualTo(GAME));
    }

    @Test
    @DisplayName("기존 회원(Member)를 삭제한다.")
    void deleteMember() {
        memberJpaRepository.delete(member);

        Optional<Member> findMember = memberJpaRepository.findById(member.getId());

        assertThat(findMember).isEmpty();   // isNull로 하면 안되는 이유?
    }

    @Test
    @DisplayName("createdBy 확인")
    void createdBy() {
        log.info("createdTime : {}", member.getCreatedAt());
//        log.info("created BY : {}", member.getCreatedBy());
        log.info("update Time : {}", member.getUpdatedAt());
    }
}