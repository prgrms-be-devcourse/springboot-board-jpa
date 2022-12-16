package com.prgrms.boardjpa.domain.member.repository;

import com.prgrms.boardjpa.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJPARepository extends JpaRepository<Member, Long> {
}
