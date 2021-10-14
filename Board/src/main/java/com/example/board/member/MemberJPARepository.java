package com.example.board.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberJPARepository extends JpaRepository<Member, Integer> {

    @Query("select m from Member as m where m.name = :name")
    Optional<Member> findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);
}
