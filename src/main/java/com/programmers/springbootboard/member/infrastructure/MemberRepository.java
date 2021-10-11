package com.programmers.springbootboard.member.infrastructure;

import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Override
    void deleteById(Long id);

    @Override
    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(Email email);

    boolean existsByEmail(Email email);

    @Override
    void deleteAll();

    @Override
    long count();

    Page<Member> findAll(Pageable pageable);
}
