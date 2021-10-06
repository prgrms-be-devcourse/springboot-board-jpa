package com.programmers.jpaboard.member.repository;

import com.programmers.jpaboard.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
