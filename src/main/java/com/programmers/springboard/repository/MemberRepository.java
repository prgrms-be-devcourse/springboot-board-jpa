package com.programmers.springboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.springboard.entity.Member;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m from member fetch join ")
    public Optional<Member> findByLoginId(String LoginId);
}
