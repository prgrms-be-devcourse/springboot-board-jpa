package com.spring.board.springboard.user.repository;

import com.spring.board.springboard.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
