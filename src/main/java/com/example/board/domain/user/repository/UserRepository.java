package com.example.board.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
