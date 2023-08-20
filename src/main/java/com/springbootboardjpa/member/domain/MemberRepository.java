package com.springbootboardjpa.member.domain;

import com.springbootboardjpa.common.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);

    default Member getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchEntityException("해당 아이디의 회원이 존재하지 않습니다."));
    }
}
