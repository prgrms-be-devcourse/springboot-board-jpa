package com.programmers.springboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.springboard.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
