package com.prgrms.springbootboardjpa.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
