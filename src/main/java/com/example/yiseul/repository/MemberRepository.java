package com.example.yiseul.repository;

import com.example.yiseul.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByOrderByIdAsc(Pageable pageable);

    List<Member> findByIdGreaterThanOrderByIdAsc(Long id);

}
