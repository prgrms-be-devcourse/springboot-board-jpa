package com.programmers.springbootboard.member.infrastructure;

import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteById(Long id);

    @Override
    Optional<Member> findById(Long aLong);

    Optional<Member> findByEmail(Email email);
}
