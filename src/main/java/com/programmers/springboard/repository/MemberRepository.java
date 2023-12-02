package com.programmers.springboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.springboard.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginId(String loginId);
}
