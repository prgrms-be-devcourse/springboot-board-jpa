package com.seungwon.board.member.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seungwon.board.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
