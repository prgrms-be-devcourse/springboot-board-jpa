package com.programmers.iyj.springbootboard.domain.user.repository;

import com.programmers.iyj.springbootboard.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
}
