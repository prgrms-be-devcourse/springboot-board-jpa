package com.example.board.domain.hobby.repository;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.hobby.entity.HobbyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

    @Query("select h from Hobby h where h.hobbyType = :hobbyType")
    Hobby findByHobbyType(HobbyType hobbyType);
}
