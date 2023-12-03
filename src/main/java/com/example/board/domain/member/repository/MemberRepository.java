package com.example.board.domain.member.repository;

import com.example.board.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmail(String email);

    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<Member> findMemberWithRolesByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM Member m WHERE m.id IN :memberIds")
    void deleteMembersByIds(List<Long> memberIds);

    @Query("SELECT m FROM Member m WHERE m.id IN :memberIds")
    List<Member> findMembersByIds(List<Long> memberIds);
}
