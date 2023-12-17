package com.example.board.domain.member.repository;

import com.example.board.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmail(String email);

    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<Member> findMemberWithRolesByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.id IN :memberIds")
    List<Member> findMembersByIds(List<Long> memberIds);

    @Query("SELECT m FROM Member m WHERE m.lastUpdatedPassword <= :sixMonthsAgo and m.isDeleted = false")
    List<Member> findNotUpdatePasswordMemberByMonth(@Param("sixMonthsAgo") LocalDateTime sixMonthsAgo);
}
