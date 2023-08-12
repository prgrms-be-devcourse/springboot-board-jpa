package com.example.jpaboard.member.service;

import com.example.jpaboard.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
