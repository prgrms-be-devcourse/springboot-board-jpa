package com.ray.springbootboard.repository;

import com.ray.springbootboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
