package com.example.board.domain.hobby.repository;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.hobby.entity.HobbyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    Hobby findByHobbyType(HobbyType hobbyType);
}
