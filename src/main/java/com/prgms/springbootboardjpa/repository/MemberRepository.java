package com.prgms.springbootboardjpa.repository;

import com.prgms.springbootboardjpa.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
