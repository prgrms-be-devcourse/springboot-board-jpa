package com.example.springboardjpa.user.repository;

import com.example.springboardjpa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
