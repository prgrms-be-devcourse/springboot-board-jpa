package com.example.springbootboardjpa.domain.member.repository;

import com.example.springbootboardjpa.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
