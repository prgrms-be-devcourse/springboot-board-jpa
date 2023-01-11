package com.example.board.domain.hobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.hobby.entity.HobbyType;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    Hobby findByHobbyType(HobbyType hobbyType);
}
