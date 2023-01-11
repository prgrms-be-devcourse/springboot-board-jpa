package com.example.board.domain.userhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.domain.userhobby.entity.UserHobby;

public interface UserHobbyRepository extends JpaRepository<UserHobby, Long> {
}
