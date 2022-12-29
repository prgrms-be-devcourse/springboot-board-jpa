package com.spring.board.springboard.user.repository;

import com.spring.board.springboard.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Query("select m from Member m where m.email = ?1")
    Optional<Member> findByEmail(String email);
}
