package com.example.boardjpa.repository;

import com.example.boardjpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
