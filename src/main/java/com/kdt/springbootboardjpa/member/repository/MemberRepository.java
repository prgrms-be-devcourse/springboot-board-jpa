package com.kdt.springbootboardjpa.member.repository;

import com.kdt.springbootboardjpa.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}