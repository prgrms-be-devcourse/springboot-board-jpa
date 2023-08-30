package com.example.yiseul.repository;

import com.example.yiseul.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select * from Member m limit :size", nativeQuery = true)
    List<Member> findFirstPage(@Param("size") int size);

    // 커서아이디 조회
    @Query(value = "select * from Member m where m.id > :cursorId limit :size", nativeQuery = true)
    List<Member> findByIdGreaterThanOrderByIdAsc(
            @Param("cursorId") Long cursorId,
            @Param("size") int size
    );

}
