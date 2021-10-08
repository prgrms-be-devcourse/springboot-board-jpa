package com.devcourse.springbootboard.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devcourse.springbootboard.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
