package com.example.board.domain.userhobby.repository;

import com.example.board.domain.userhobby.entity.UserHobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHobbyRepository extends JpaRepository<UserHobby, Long> {
}
