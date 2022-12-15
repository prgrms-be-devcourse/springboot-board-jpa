package com.programmers.jpaboard.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpaboard.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
