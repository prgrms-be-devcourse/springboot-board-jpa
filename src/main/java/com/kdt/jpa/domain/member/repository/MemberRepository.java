package com.kdt.jpa.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdt.jpa.domain.member.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
