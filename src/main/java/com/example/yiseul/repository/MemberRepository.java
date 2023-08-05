package com.example.yiseul.repository;

import com.example.yiseul.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByOrderByIdAsc(Pageable pageable);

    List<Member> findByIdGreaterThanOrderByIdAsc(Long id, Pageable pageable);

}
