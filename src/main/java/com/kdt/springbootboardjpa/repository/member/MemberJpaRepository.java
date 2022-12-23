package com.kdt.springbootboardjpa.repository.member;

import com.kdt.springbootboardjpa.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}