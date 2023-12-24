package com.programmers.springboard.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.springboard.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginId(String loginId);
}
