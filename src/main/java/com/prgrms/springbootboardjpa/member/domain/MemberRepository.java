package com.prgrms.springbootboardjpa.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.email = :email and m.password = :password")
    Optional<Member> findMemberForLogin(@Param("email") String email, @Param("password") String password);
}
