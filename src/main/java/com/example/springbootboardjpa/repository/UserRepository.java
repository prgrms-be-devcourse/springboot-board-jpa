package com.example.springbootboardjpa.repository;

import com.example.springbootboardjpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
